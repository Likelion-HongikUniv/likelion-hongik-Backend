package Likelion.Recruiting.config.auth.jwt;

import Likelion.Recruiting.config.auth.CustomOAuth2UserService;
import Likelion.Recruiting.config.auth.CustomOauthUser;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.JWTException;
import Likelion.Recruiting.exception.RefreshException;
import Likelion.Recruiting.model.RefreshToken;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.repository.RefreshTokenRepository;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.service.TokenService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${app.auth.token.secret-key}")
    private String SECRET_KEY;

    // JWT 토큰 유효시간 설정
    private Long tokenValidTime = 60 * 60 * 1000L; // 1시간
    // Refresh Token 유효시간 설정
    private Long RTValidTime = 10 * 1000L; // 1초
//    private Long RTValidTime = 14 * 24 * 60 * 60 * 1000L; // 2주

    //secretkey를 미리 인코딩 해줌.
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

//---------------------- JWT 토큰 생성 ---------------------- //
    public String createToken(String email, Role role) {

        //payload 설정
        //registered claims
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("access_token") //토큰제목
                .setIssuedAt(now) //발행시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)); // 토큰 만료기한

        //private claims
        claims.put("email", email); // 정보는 key - value 쌍으로 저장.
        claims.put("role", role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT") //헤더
                .setClaims(claims) // 페이로드
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅
                .compact();
    }

//---------------------- RT 토큰 생성 ---------------------- //
    public String createRT(String email, Role role) {

        //payload 설정
        //registered claims
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("refresh_token") //토큰제목
                .setIssuedAt(now) //발행시간
                .setExpiration(new Date(now.getTime() + RTValidTime)); // 토큰 만료기한

        claims.put("email", email); // 정보는 key - value 쌍으로 저장.
        claims.put("role", role);

        return Jwts.builder()
                .setHeaderParam("typ", "refreshToken") //헤더
                .setClaims(claims) // 페이로드
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅
                .compact();
    }

//---------------------- JWT 토큰에서 인증정보 조회 ---------------------- //
    public Authentication getAuthentication(String token){
        // token에서 복호화한 email을 가진 user객체를 customOauthUser 형태로 뽑아오기
        CustomOauthUser user = customOAuth2UserService.loadUserByEmail(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }


//---------------------- token에서 user email 추출하기 ---------------------- //
    private String getUserPk(String token) {
        return (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("email");
    }



//---------------------- request의 Header에서 JWT만 뽑아오기 ---------------------- //
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("JWT");
    }

//---------------------- request의 Header에서 RT만 뽑아오기 ---------------------- //
    public String resolveRT(HttpServletRequest request){
        return request.getHeader("refresh_token");
    }

//---------------------- jwt 유효성 검사하기 ---------------------- //
    public boolean validateToken(String jwtToken){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
            // 지금과 비교했을 때 토큰의 만료시간이 전(before)인가? -> 토큰이 만료 되었으면 true / 안 됐으면 false 반환
            return !claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e){
            System.out.println("1e = " + e.getMessage());
            throw new JWTException("토큰 기한 만료", ErrorCode.EXPIRED_JWT.getErrorCode());
        } catch (SignatureException e){
            System.out.println("2e = " + e.getClass());
            throw new JWTException("사용자 인증 실패", ErrorCode.NOT_VALIDATE_JWT.getErrorCode());
        }
    }

//---------------------- refresh_token 유효성 검사하기 ---------------------- //
    public String validateRT(String refreshToken){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(refreshToken).getBody();

            // 지금과 비교했을 때 토큰의 만료시간이 전(before)인가? -> 토큰이 만료 되었으면 true / 안 됐으면 false 반환
            return "ok";
        } catch (ExpiredJwtException e){ // RT 기한 만료
            System.out.println("rt 1e = " + e.getMessage());

            return "expired";
        } catch (SignatureException e){
            System.out.println("rt 2e = " + e.getClass());
            throw new JWTException("사용자 인증 실패", ErrorCode.NOT_VALIDATE_RT.getErrorCode());
        }
    }

}

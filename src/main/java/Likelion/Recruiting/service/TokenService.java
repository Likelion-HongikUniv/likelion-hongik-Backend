package Likelion.Recruiting.service;

import Likelion.Recruiting.config.auth.jwt.JwtTokenProvider;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.RefreshException;
import Likelion.Recruiting.model.RefreshToken;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.model.dto.JWTDto;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.repository.RefreshTokenRepository;
import Likelion.Recruiting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    public User getUser(String token){
        String email = jwtTokenProvider.getUserEmail(token);
        User user = userRepository.findByEmail(email).get();

        return user;
    }
    public NavbarDto login(Long uid){
        // 해당 유저 찾기
        User user = userRepository.findById(uid).get();

        String email = user.getEmail();
        Role role = user.getRole();

        // JWT 만들기
        String token = jwtTokenProvider.createToken(email, role);

        return NavbarDto.builder()
                .id(user.getId())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .isJoined(user.isJoind())
                .role(user.getRole())
                .JWT(token)
                .build();
    }

    public JWTDto makeJwt(Long uid){

        // 해당 유저 찾기
        User user = userRepository.findById(uid).get();

        String email = user.getEmail();
        Role role = user.getRole();

        // JWT 만들기
        String token = jwtTokenProvider.createToken(email, role);

        return new JWTDto(token);
    }

    @Transactional
    public String makeRt(Long uid){
        // 해당 유저 찾기
        User user = userRepository.findById(uid).get();

        String email = user.getEmail();
        Role role = user.getRole();

        // Refresh Token 만들기
        String RT = jwtTokenProvider.createRT(email, role);

        // RT를 저장하기 위헤 객체로 만들기
        RefreshToken refreshToken = RefreshToken.builder()
                .user_id(user.getId())
                .refreshToken(RT)
                .build();

        // RT DB에 저장하기
        refreshTokenRepository.save(refreshToken);

        return RT;
    }

    @Transactional
    public void deleteRt(String rt){
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(rt);
        if(refreshToken != null){ // DB에 Refresh Token이 있는 경우
            refreshTokenRepository.delete(refreshToken);
            System.out.println("rt 삭제 완료!");
        }
        else { // DB에 Refresh Token이 없는 경우 == 이미 삭제 되었거나, 올바르지 않은 RT이거나
            throw new RefreshException(ErrorCode.NOT_VALIDATE_RT.getErrorCode(), "refresh_token이 올바르지 않습니다.");
        }

    }
}

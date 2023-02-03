package Likelion.Recruiting.config.auth;

import Likelion.Recruiting.config.auth.jwt.JwtTokenProvider;
import Likelion.Recruiting.model.enums.LType;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인 실패 후 성공 시 남아있는 에러 세션 제거
//        clearSession(request);
        System.out.println("handler속");
        OAuth2User oAuth2User1 = (OAuth2User) authentication.getPrincipal();
//        System.out.println("oAuth2User1 att = " + oAuth2User1.getAttributes());
//        System.out.println("oAuth2User1 name= " + oAuth2User1.getName());
        User user = new User("", "", LType.GOOGLE, Role.GUEST, "");
        if (oAuth2User1.getAttributes().get("kakao_account") != null){
            Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User1.getAttributes().get("kakao_account");
            String email = (String) kakao_account.get("email");
            user = userRepository.findByEmail(email).get();
        }
        else if(oAuth2User1.getAttributes().get("email") == null){
            System.out.println("gkgkgk");
            user = userRepository.findByEmail(oAuth2User1.getAttributes().get("id").toString()).get();
        }
        else {
            // 해당 email을 가진 유저 객체 가져오기
            System.out.println("sdf = " + oAuth2User1.getAttributes());
            try {
                user = userRepository.findByEmail(oAuth2User1.getAttributes().get("email").toString()).get();
            }catch (NullPointerException e){
//               throw
            }

        }

        // JWT 속 암호화 할 정보들 세팅하기
        String email = user.getEmail();
        Role role = user.getRole();

        // JWT 만들기
        String token = jwtTokenProvider.createToken(email, role);
        System.out.println("filter token = " + token);

        response.setHeader("JWT", token);

        String tartgetUri;
        tartgetUri = UriComponentsBuilder
                .fromUriString("http://localhost:3000/")
                .path("/ing")
                .queryParam("token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, tartgetUri);
    }

    // 로그인 실패 후 성공 시 남아있는 에러 세션 제거
    protected void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}

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

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("handler속");
        // 로그인 성공한 authentication 객체를 소셜로그인 객체인 OAuth2User로 변경하기
        //google - sub
        //kakao - oAuth2User1.getAttributes().get("kakao_account")
        //github - oAuth2User1.getAttributes().get("avatar_url")
        System.out.println(authentication.getPrincipal());
        OAuth2User oAuth2User1 = (OAuth2User) authentication.getPrincipal();
        String SocialType = "";


        // 일단 빈 user 객체 생성하기 -> 밑 if문 마다 생성하지 않기 위해서 -> 정보 덮여쓰기가 나을 거 같아서 이렇게 함.
        User user = new User("", "", LType.GOOGLE, Role.GUEST, "");
        if (oAuth2User1.getAttributes().get("sub") != null){
            SocialType = "GOOGLE";
            user = userRepository.findByEmail(oAuth2User1.getAttributes().get("email").toString()).get();
        }
        else if (oAuth2User1.getAttributes().get("kakao_account") != null){ // 카카오 로그인이라면
            SocialType = "KAKAO";
            Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User1.getAttributes().get("kakao_account");
            String email = (String) kakao_account.get("email");
            user = userRepository.findByEmail(email).get();
        }
        else if(oAuth2User1.getAttributes().get("avatar_url") != null){ // 깃허브 로그인이라면
            SocialType = "GITHUB";
            user = userRepository.findByEmail(oAuth2User1.getAttributes().get("id").toString()).get();
        }
        else { //네이버 로그인이라면
            // 해당 email을 가진 유저 객체 가져오기
            SocialType = "NAVER";
            user = userRepository.findByEmail(oAuth2User1.getAttributes().get("email").toString()).get();
        }

        String email = user.getEmail();
        Long userId = userRepository.findByEmail(email).get().getId();

        if (!(user.getLType().toString().equals(SocialType))){
            userId = (long)-1;
        }

        String tartgetUri;
        tartgetUri = UriComponentsBuilder
                .fromUriString("http://likelionhongik.com/")
                .path("/ing")
                .queryParam("UID", userId)
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

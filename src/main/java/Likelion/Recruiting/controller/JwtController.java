package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.JwtTokenProvider;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 여기서 JWT를 돌려줄 것이다.
    @GetMapping("/v1/token")
    public void testOAuthLogin(Authentication authentication, HttpServletResponse response) throws IOException {


        OAuth2User oAuth2User1 = (OAuth2User) authentication.getPrincipal();
        // 해당 email을 가진 유저 객체 가져오기
        User user = userRepository.findByEmail(oAuth2User1.getAttributes().get("email").toString()).get();
        System.out.println("user.getEmail() = " + user.getEmail());
        // JWT 속 암호화 할 정보들 세팅하기
        String email = user.getEmail();
        Role role = user.getRole();

        // JWT 만들기
        String token = jwtTokenProvider.createToken(email, role);
        System.out.println("token = " + token);

        // 응답 헤더에 JWT 넣기
        response.setHeader("Authorization", token);
        // redirect 할 링크 설정
//        response.sendRedirect("http://localhost:3000/");
        String uri;
//        uri = UriComponentsBuilder.fromUriString("/login").queryParam("JWT", "token").build().toUriString();


//        return "http://localhost:3000/";
    }
}

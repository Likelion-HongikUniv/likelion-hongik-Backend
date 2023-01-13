package com.recruit.test.controller;

import com.recruit.test.config.auth.CustomOauthUserImpl;
import com.recruit.test.config.auth.JwtTokenProvider;
import Likelion.model.Role;
import Likelion.model.User;
import com.recruit.test.model.UserRepository;
import Likelion.model.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class JwtController {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 여기서 JWT를 돌려줄 것이다.
    @GetMapping("/token")
    public String testOAuthLogin(@AuthenticationPrincipal OAuth2User userDetails, HttpServletResponse response){

        Optional<User> user = userRepository.findByEmail(userDetails.getAttributes().get("email").toString());
        CustomOauthUserImpl customOauthUser = new CustomOauthUserImpl(user.get(), userDetails.getAttributes());

        String email = customOauthUser.getUser().getEmail();
        Role role = customOauthUser.getUser().getRole();

        String token = jwtTokenProvider.createToken(email, role);
        System.out.println("token = " + token);
        response.setHeader("JWT", token);


        return "/";
    }
}

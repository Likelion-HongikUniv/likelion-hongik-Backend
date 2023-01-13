package com.recruit.test.controller;

import com.recruit.test.config.auth.CustomOauthUser;
import com.recruit.test.config.auth.CustomOauthUserImpl;
import com.recruit.test.config.auth.JwtTokenProvider;
import com.recruit.test.config.auth.dto.SessionUser;
import com.recruit.test.model.Profile;
import com.recruit.test.model.Role;
import com.recruit.test.model.User;
import com.recruit.test.model.UserRepository;
import com.recruit.test.model.dto.ProfileDto;
import com.recruit.test.repository.ProfileRepository;
import com.recruit.test.security.UserDetailsImpl;
import com.recruit.test.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpHeaders;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class indexController {

    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @GetMapping("/")
    String index(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, Model model) {
//        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        System.out.println("userDetails = " + customOauthUser);
        if (customOauthUser != null) {
            User user = customOauthUser.getUser();
//            System.out.println("user.getRole() = " + user.getRole());
            model.addAttribute("message", user);

        } else {
            model.addAttribute("message", "hello~");
        }
//        if(sessionUser != null){
//            model.addAttribute("message", sessionUser.getName());
//            Optional<User> user = userRepository.findByEmail(sessionUser.getEmail());
//
//        }
//        else{
//            model.addAttribute("message", "hello~");
//        }

        return "index2";
    }


    @GetMapping("/loginForm")
    public String loginForm() {
//        System.out.println("여기는 로그인 폼이지롱~");
        return "loginForm";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal OAuth2User userDetails) {
//        Optional<User> user = userRepository.findByEmail(userDetails.getAttributes().get("email").toString());
//        UserDetailsImpl userDetails1 = new UserDetailsImpl(user.get(), userDetails.getAttributes());
        System.out.println("userDetails = " + userDetails.getAttributes());
//        System.out.println("userDetails1.getUser().getEmail() = " + userDetails1.getUser().getEmail());
//
//        int userId = userDetails1.getUser().getId();
//        Profile profile = Profile.builder()
//                .
//        System.out.println("profileDto = " + profileDto);

        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @PostMapping("/accounts/detail_info/")
    String detail(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto) {
        Optional<User> user = userRepository.findByEmail(customOauthUser.getUser().getEmail());

        profileService.insertDetail(customOauthUser.getUser(), profileDto);

        return "detail";
    }

    @GetMapping("/accounts/detail_info/")
    String detail2(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto) {

        // 로그인 유저의 email 추출
        Optional<User> user = userRepository.findByEmail(customOauthUser.getAttributes().get("email").toString());
        // profile_id와 엮을 user_id 추출
        int userId = user.get().getId();

        System.out.println("profileDto = " + profileDto.getMajor());

        return "detail";
    }

    @GetMapping("/mypage")
    public @ResponseBody Profile myPage(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser) {

        // 로그인 유저의 email 추출
        //Optional<User> user = userRepository.findByEmail(customOauthUser.getAttributes().get("email").toString());
        // profile_id와 엮을 user_id 추출
        User user = customOauthUser.getUser();
        Profile profile = profileService.viewProfile(user);
        return profile;
    }
}
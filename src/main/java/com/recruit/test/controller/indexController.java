package Likelion.controller;

import com.recruit.test.config.auth.CustomOauthUserImpl;
import com.recruit.test.config.auth.JwtTokenProvider;
import Likelion.model.entity.Profile;
import com.recruit.test.model.UserRepository;
import Likelion.model.dto.dto.ProfileDto;
import Likelion.service.ProfileService;
import Likelion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class indexController {

    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;

    @GetMapping("/")
    String index(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, Model model){
//        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        System.out.println("userDetails = " + customOauthUser);
        if(customOauthUser != null){
            User user = customOauthUser.getUser();
//            System.out.println("user.getRole() = " + user.getRole());
            model.addAttribute("message", user);

        }
        else{
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
    public String loginForm(){
//        System.out.println("여기는 로그인 폼이지롱~");
        return "loginForm";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal OAuth2User userDetails){
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
    public @ResponseBody String admin(){
        return "admin";
    }

    @PostMapping("/accounts/detail_info/")
    String detail(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto){
       // 지금 로그인되어 있는 User 객체 정보 찾기
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);

        // 처음 로그인 한 것이지 확인하기 == Profile db에 해당 user의 정보가 있는지 확인하기
        Integer user_id = user.getId();
        System.out.println("user_id = " + user_id);

        Profile profile = profileService.haveUser(user_id);
        if (profile != null){
            System.out.println("프로필이 이미 있습니다!");
            return "already";
        }
        else {
            System.out.println("어서오세요~~~");
            profileService.insertDetail(customOauthUser.getUser(), profileDto);
        }

        return "detail";
    }

    @GetMapping("/accounts/detail_info/")
    String detail2(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto){

        return "detail";
    }

}

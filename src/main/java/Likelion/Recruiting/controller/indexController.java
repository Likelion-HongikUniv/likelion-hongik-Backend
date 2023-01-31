package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.exception.DuplicationException;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.model.dto.*;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@Controller
public class indexController {

    private final UserService userService;

    @GetMapping("/")

    String index(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, Model model){

        if(customOauthUser != null){
            User user = customOauthUser.getUser();
            model.addAttribute("message", user);

        }
        else{
            model.addAttribute("message", "hello~");
        }

        return "index2";
    }


    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal OAuth2User userDetails){

        return "users";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @ResponseBody
    @PostMapping("/accounts/detail_info/")
    IsMemberDto detail(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto){
        // 지금 로그인되어 있는 User 객체 정보 찾기
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);

        if (userService.validateNickname(profileDto.getNickname()) != null){ // 닉네임이 중복된다면 에러 메세지를 리턴
            throw new DuplicationException(ErrorCode.DUPLICATE_NICKNAME.getErrorCode(), ErrorCode.DUPLICATE_NICKNAME.getErrorMessage());
        }

//        if (user.isJoind() == false && user.getRole() == Role.USER) // 멋사 합격자이면서 추가정보 안 받은 사람 -> 회원가입
        if( user.isJoind() == false&& user.getRole() == Role.USER){ // 아직 추가 정보 안 받음 -> 멋사 회원가입 전 -> isJoined == true 여야 함
            userService.insertDetail(email, profileDto);
            return new IsMemberDto(user.isJoind(), user.getRole()); // true, USER
        }
        else if (user.isJoind() == true && user.getRole() == Role.USER){ // 멋사 합격자이면서 추가정보 받은 사람 -> 그냥 로그인
            return new IsMemberDto(user.isJoind(), user.getRole()); // true, USER
        }
        else { // 멋사 멤버가 아닌 사람(불합격자 or 그냥 재미로 소셜로그인 한 사람) // false GUEST
            return new IsMemberDto(user.isJoind(), user.getRole());
        }

    }

}

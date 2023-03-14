package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.exception.DuplicationException;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.model.dto.*;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.service.TokenService;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class indexController {

    private final UserService userService;
    private final TokenService tokenService;

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


   @GetMapping("/out")
//   public void logout(@CookieValue("refreshToken") String cookie){
   public ResponseDto logout(HttpServletResponse response, @CookieValue("refreshToken") String cookie){
        System.out.println("dd");
        tokenService.deleteRt(cookie);
        System.out.println("로그아웃 완료");
        ResponseCookie res_cookie = ResponseCookie.from("refreshToken", null)
               .path("/") // 해당 쿠키를 도메인 전체에서 사용하고 싶다면 9번 라인같이 "/"를 Path로 준다.
               .secure(true)
               .sameSite("None")
               .httpOnly(true)
               .build();
        response.setHeader("Set-Cookie", res_cookie.toString());
        return ResponseDto.builder()
                .code(200)
                .httpStatus(HttpStatus.OK)
                .message("로그아웃되었습니다.")
                .build();
   }

    @ResponseBody
    @PostMapping("/accounts/detail_info/")
    IsMemberDto detail(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto){
        // 지금 로그인되어 있는 User 객체 정보 찾기
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);

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

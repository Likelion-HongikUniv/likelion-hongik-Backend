package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.config.auth.JwtTokenProvider;
import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.dto.*;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.service.CommentService;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Controller
public class indexController {

    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

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
    public @ResponseBody List<User> user(@AuthenticationPrincipal OAuth2User userDetails){
        List<User> users = userService.findUserAll();

        return users;
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

        // 처음 로그인 한 것이지 확인하기 == Profile db에 해당 user의 정보가 있는지 확인하기
        Long user_id = user.getId();
        System.out.println("user.isJoind() = " + user.isJoind());
//        if (user.isJoind() == false && user.getRole() == Role.USER) // 멋사 합격자이면서 추가정보 안 받은 사람 -> 회원가입
        if( user.isJoind() == false){ // 아직 추가 정보 안 받음 -> 멋사 회원가입 전 -> isJoined == true 여야 함
            userService.insertDetail(email, profileDto);
//            return "detail";
            return new IsMemberDto(user.isJoind(), user.getRole()); // false, USER
        }
        else if (user.isJoind() == true && user.getRole() == Role.USER){ // 멋사 합격자이면서 추가정보 받은 사람 -> 그냥 로그인
            return new IsMemberDto(user.isJoind(), user.getRole()); // true, USER
        }
        else { // 멋사 멤버가 아닌 사람(불합격자 or 그냥 재미로 소셜로그인 한 사람)
            return new IsMemberDto(user.isJoind(), user.getRole());
//            return new IsMemberDto(user.isJoind()); // false, GUEST
        }

    }
    @ResponseBody
    @GetMapping("/mypage/")
    ProfileDto user_info (@AuthenticationPrincipal CustomOauthUserImpl customOauthUser){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);

        return new ProfileDto(
                user.getNickname(),
                user.getMajor(),
                user.getStudentId(),
                user.getPart(),
                user.getPhoneNum());
    }

    @ResponseBody
    @PatchMapping("/mypage/edit/")
    ProfileDto mypage_edit(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        user = user.profileUpdate(profileDto.getNickname(), profileDto.getMajor(), profileDto.getStudentId(), profileDto.getPart(), profileDto.getPhoneNum());
        return new ProfileDto(
                user.getNickname(),
                user.getMajor(),
                user.getStudentId(),
                user.getPart(),
                user.getPhoneNum());
    }

    @GetMapping("/mypage/comments/")
    List<PostDetailDto> myComments (@AuthenticationPrincipal CustomOauthUserImpl customOauthUser) {
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);

        List<Comment> comments = commentService.findUser_Comment(user);
        List<Post> post =  postService.findByComment(comments);
        List<PostDetailDto> result = post.stream()
                .map(p -> new PostDetailDto(p,user))
                .collect(toList());
        return result;
    }

}

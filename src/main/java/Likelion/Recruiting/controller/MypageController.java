package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class MypageController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/profile")
    public NavbarDto getNameandImage(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser){

        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        // navbarDto에 넣기
        NavbarDto navbarDto = userService.navProfile(email);

        return navbarDto;
    }


    @GetMapping("/mypage/post")
    public Page<PostDto> getMyPosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, Pageable pageable){
        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        // dto 써서 return 하기
        Page<PostDto> posts = postService.getMyAllPost(email, pageable);

        return posts;
    }




}

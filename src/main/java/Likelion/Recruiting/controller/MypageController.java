package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.service.LikeService;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final LikeService likeService;

    @GetMapping("/profile")
    public NavbarDto getNameandImage(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser){

        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        // navbarDto에 넣기
        NavbarDto navbarDto = userService.navProfile(email);

        return navbarDto;
    }

    @GetMapping("/mypage/post")
    public Page<Post> getMyPosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PageableDefault(size = 10, sort = "id")Pageable pageable){
        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        // dto 써서 return 하기
        Page<Post> postDtos = postService.getMyAllPost(email, pageable);
        return postDtos;
    }

    @GetMapping("/mypage/like")
    public List<PostDto> getMyLikedPosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PageableDefault(size = 10, sort = "id")Pageable pageable){

        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        Long user_id = userService.findUser(email).getId();

        List<PostDto> postDtos = likeService.getLikedPost(user_id);

        return postDtos;
    }
    


}

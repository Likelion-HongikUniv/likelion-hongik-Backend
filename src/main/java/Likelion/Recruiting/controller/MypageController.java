package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;

import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.model.dto.ProfileDto;
import Likelion.Recruiting.service.CommentService;
import Likelion.Recruiting.service.LikeService;
import Likelion.Recruiting.service.PostService;

import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class MypageController {

    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping("/profile")
    public NavbarDto getNameandImage(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser){

        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        // navbarDto에 넣기
        NavbarDto navbarDto = userService.navProfile(email);

        return navbarDto;
    }


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


    @GetMapping("/mypage/post")
    public Page<PostDto> getMyPosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,
                                    @PageableDefault(size=5, sort="createdTime" ,direction = Sort.Direction.DESC) Pageable pageable){
        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        Page<PostDto> posts = postService.getMyAllPost(email, pageable);

        return posts;
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

    @GetMapping("/mypage/like")
    public Page<PostDto> getMyLikedPosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, Pageable pageable){

        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        Long user_id = userService.findUser(email).getId();

//        List<PostDto> postDtos = likeService.getLikedPost(user_id);
        Page<PostDto> posts = likeService.getLikedPost(user_id, pageable);

        return posts;
    }




}

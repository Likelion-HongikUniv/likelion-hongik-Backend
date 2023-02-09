package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.exception.DuplicationException;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;

import Likelion.Recruiting.model.dto.*;
import Likelion.Recruiting.service.CommentService;
import Likelion.Recruiting.service.LikeService;
import Likelion.Recruiting.service.PostService;

import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class MypageController {
    //test

    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;

//    @GetMapping("/profile")
//    public NavbarDto getNameandImage(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser){
//        // 유저의 email 뽑아내기
//        String email = customOauthUser.getUser().getEmail();
//
//        // navbarDto에 넣기
//        NavbarDto navbarDto = userService.navProfile(email);
//
//        return navbarDto;
//    }

    @PostMapping("/nickname")
    public ResponseDto validateNickname(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody NicknameDto nickname){
        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        if (userService.validateNickname(nickname.getNickname()) != null){ // 닉네임이 중복된다면 에러 메세지를 리턴
            throw new DuplicationException(ErrorCode.DUPLICATE_NICKNAME.getErrorCode(), ErrorCode.DUPLICATE_NICKNAME.getErrorMessage());
        }
        else {
            return ResponseDto.builder()
                    .code(200)
                    .httpStatus(HttpStatus.OK)
                    .message("닉네임이 중복되지 않았습니다!")
                    .build();
        }
    }

    @GetMapping("/userinfo")
    UserAllDto allOfUser(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser)  {
        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();
        System.out.println("email = " + email);

        return userService.getAllAboutUser(email);
    }


    @GetMapping("/mypage")
    ProfileDto user_info (@AuthenticationPrincipal CustomOauthUserImpl customOauthUser)  {
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);

        return new ProfileDto(
                user.getNickname(),
                user.getMajor(),
                user.getStudentId(),
                user.getPart());
    }

    @PatchMapping("/mypage/edit")
    ProfileDto mypage_edit(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody ProfileDto profileDto){
        String email = customOauthUser.getUser().getEmail();

        User user = userService.editProfile(email, profileDto);

        return new ProfileDto(
                user.getNickname(),
                user.getMajor(),
                user.getStudentId(),
                user.getPart());
    }


    @GetMapping("/mypage/posts")

    public Page<PostDto> getMyPosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,
                                    @PageableDefault(size=5, sort="createdTime" ,direction = Sort.Direction.DESC) Pageable pageable){
        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        Page<PostDto> posts = postService.getMyAllPost(email, pageable);

        return posts;
    }

    @GetMapping("/mypage/comments")
    public Page<PostDto> myComments (@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,
                                    @PageableDefault(size=5, sort="createdTime" ,direction = Sort.Direction.DESC) Pageable pageable) {
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);

//        List<Comment> comments = commentService.findUser_Comment(user);
//        List<Post> post =  postService.findByComment(comments);
        Page<PostDto> result = postService.getPosts(user.getId(), pageable);

        return result;
    }

    @GetMapping("/mypage/likes")
    public Page<PostDto> getMyLikedPosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,
                                         @PageableDefault(size=5, sort="createdTime" ,direction = Sort.Direction.DESC)Pageable pageable){

        // 유저의 email 뽑아내기
        String email = customOauthUser.getUser().getEmail();

        Long user_id = userService.findUser(email).getId();

        Page<PostDto> posts = likeService.getLikedPost(user_id, pageable);

        return posts;
    }
}

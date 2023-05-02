package Likelion.Recruiting.domain.comment.controller;

import Likelion.Recruiting.common.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.common.dto.CreateResponseMessage;
import Likelion.Recruiting.common.dto.DataResponseDto;
import Likelion.Recruiting.domain.comment.dto.CommentDto;
import Likelion.Recruiting.domain.comment.dto.CreateCommentRequestDto;
import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.comment.entity.CommentLike;
import Likelion.Recruiting.domain.comment.repository.CommentLikeRepository;
import Likelion.Recruiting.domain.comment.repository.CommentRepository;
import Likelion.Recruiting.domain.comment.service.CommentLikeService;
import Likelion.Recruiting.domain.comment.service.CommentService;
import Likelion.Recruiting.domain.post.dto.CreatePostResponseDto;
import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.repository.PostRepository;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.user.service.UserService;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final UserService userService;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    //------------------------------------댓글------------------------

    @GetMapping("/community/post/{postId}/comments")//게시글에 따른 댓글 & 대댓글 불러오기
    public DataResponseDto getSimplePosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestHeader("HEADER") String header, @PathVariable("postId") Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);// 옵셔널이므로 id없을시 예외처리할때 예외코드날아감 -->try catch쓰기
        List<CommentDto> result = comments.stream()
                .map(comment -> new CommentDto(comment,user))
                .collect(Collectors.toList());
        return new DataResponseDto(result.size(), result);
    }
    @PostMapping("/community/post/{postId}")//댓글 저장 api
    public CreatePostResponseDto saveComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreateCommentRequestDto request, @PathVariable("postId") Long postId) {
        Comment createdComment = Comment.builder()
                .body(request.getBody())
                .build();
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        Post post = postRepository.findById(postId).get();//역시 예외처리는 예외코드로
        Comment savedComment = commentService.createComment(createdComment,post,user);
        return new CreatePostResponseDto(savedComment.getId(), "댓글 작성 성공");
    }
    @PatchMapping("/community/comment/{commentId}") //예외처리~~~
    public CreateResponseMessage updateComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@RequestBody CreateCommentRequestDto request, @PathVariable("commentId") Long commentId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return commentService.updateComment(commentId,user.getId(),request);
    }
    @DeleteMapping("/community/comment/{commentId}") //예외처리~~~
    public CreateResponseMessage deleteComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("commentId") Long commentId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return commentService.deleteComment(commentId,user.getId());
    }
    @PostMapping("/community/comment/{commentId}/like") //댓글좋아용(이미 있으면 삭제, 없으면 저장)
    public CreateResponseMessage likeComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findById(commentId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);//   유저는 헤더파일에서 뽑아오기
        CommentLike commentLike = commentLikeRepository.findOneByUserAndComment(user,comment);
        if (commentLike == null){
            commentLikeService.createCommentLike(user,comment);
        }
        else commentLikeService.deleteCommentLike(user,comment);

        return new CreateResponseMessage((long)200, "좋아요 성공");
    }
}

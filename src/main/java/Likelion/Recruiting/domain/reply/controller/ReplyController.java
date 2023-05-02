package Likelion.Recruiting.domain.reply.controller;

import Likelion.Recruiting.common.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.common.dto.CreateResponseMessage;
import Likelion.Recruiting.domain.comment.dto.CreateCommentRequestDto;
import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.comment.repository.CommentRepository;
import Likelion.Recruiting.domain.post.dto.CreatePostResponseDto;
import Likelion.Recruiting.domain.reply.entity.Reply;
import Likelion.Recruiting.domain.reply.entity.ReplyLike;
import Likelion.Recruiting.domain.reply.repository.ReplyLikeRepository;
import Likelion.Recruiting.domain.reply.repository.ReplyRepository;
import Likelion.Recruiting.domain.reply.service.ReplyLikeService;
import Likelion.Recruiting.domain.reply.service.ReplyService;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.user.service.UserService;


import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final ReplyLikeService replyLikeService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    private final ReplyRepository replyRepository;
    private final ReplyLikeRepository replyLikeRepository;
    //------------------------------------대댓글------------------------
    @PostMapping("/community/comment/{commentId}")//대댓글 저장 api
    public CreatePostResponseDto saveReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreateCommentRequestDto request, @PathVariable("commentId") Long commentId) {
        Reply createdReply = Reply.builder()
                .body(request.getBody())
                .build();
        // user insert partition
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        Comment comment = commentRepository.findById(commentId).get();//역시 예외처리는 예외코드로
        Reply savedReply = replyService.createReply(createdReply,comment,user);
        return new CreatePostResponseDto(savedReply.getId(),"대댓글 작성 성공");
    }
    @PatchMapping("/community/reply/{replyId}") //예외처리~~~
    public CreateResponseMessage updateReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@RequestBody CreateCommentRequestDto request, @PathVariable("replyId") Long replyId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return replyService.updateReply(replyId,user.getId(),request);
    }
    @DeleteMapping("/community/reply/{replyId}") //예외처리~~~
    public CreateResponseMessage updateReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("replyId") Long replyId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return replyService.deleteReply(replyId,user.getId());
    }

    @PostMapping("/community/reply/{replyId}/like") // 대댓글좋아요(이미 있으면 삭제, 없으면 저장)
    public CreateResponseMessage likeReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("replyId") Long replyId){
        Reply reply= replyRepository.findById(replyId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email); //   유저는 헤더파일에서 뽑아오기
        ReplyLike replyLike = replyLikeRepository.findOneByUserAndReply(user,reply);
        if (replyLike == null){
            replyLikeService.createReplyLike(user,reply);
        }
        else replyLikeService.deleteReplyLike(user,reply);
        return new CreateResponseMessage((long)200, "좋아요 성공");
    }
}



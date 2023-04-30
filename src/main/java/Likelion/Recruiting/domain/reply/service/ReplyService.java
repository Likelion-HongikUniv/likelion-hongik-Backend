package Likelion.Recruiting.domain.reply.service;

import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.reply.entity.Reply;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.comment.dto.CreateCommentRequestDto;
import Likelion.Recruiting.common.dto.CreateResponseMessage;
import Likelion.Recruiting.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Reply createReply(Reply reply, Comment comment, User user){
        reply.setAuthor(user);
        reply.setComment(comment);
        Reply createdReply = replyRepository.save(reply);
        return createdReply;
    }

    @Transactional
    public CreateResponseMessage updateReply(Long replyId, Long userId, CreateCommentRequestDto reqeust){
        Reply reply = replyRepository.findById(replyId).get();
        if(userId.equals(reply.getAuthor().getId())){
            reply.update(reqeust.getBody());
            replyRepository.save(reply);
            return new CreateResponseMessage((long)200, "업데이트 성공");
        }
        else return new CreateResponseMessage((long)403, "본인의 댓글이 아닙니다.");
    }


    @Transactional
    public CreateResponseMessage deleteReply (Long replyId, Long userId){
        Reply reply = replyRepository.findById(replyId).get();
        if(userId.equals(reply.getAuthor().getId())) {
            reply.delete();
            if (reply.getIsDeleted() == true)
                return new CreateResponseMessage((long) 200, "삭제 성공");
            else return new CreateResponseMessage((long) 404, "이미 삭제된 댓글입니다.");
        }
        else return new CreateResponseMessage((long)403, "본인의 댓글이 아닙니다.");
    }
}

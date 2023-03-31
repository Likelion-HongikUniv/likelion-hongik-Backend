package Likelion.Recruiting.service;

import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.CreateCommentRequestDto;
import Likelion.Recruiting.model.dto.CreateResponseMessage;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.CommentRepository;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Reply createReply(Reply reply,Comment comment, User user){
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

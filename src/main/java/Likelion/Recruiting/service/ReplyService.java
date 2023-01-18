package Likelion.Recruiting.service;

import Likelion.Recruiting.model.*;
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
    @Autowired
    private final ReplyRepository replyRepository;

    @Transactional
    public Reply createReply(Reply reply,Comment comment, User user){
        reply.setAuthor(user);
        reply.setComment(comment);
        Reply createdReply = replyRepository.save(reply);
        return createdReply;
    }

    @Transactional
    public Reply updateReply(Reply reply, String body){
        reply.update(body);
        return reply;
    }


    @Transactional
    public Reply deleteReply (Reply reply){
        reply.delete(); // 진짜 삭제가 아니라 불값만 변경 ==>> 객체는 있으나 프론트에서 표기만 ~~
        return reply;
    }

}

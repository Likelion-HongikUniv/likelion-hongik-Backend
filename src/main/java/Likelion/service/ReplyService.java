package Likelion.service;


import Likelion.model.entity.Comment;
import Likelion.model.entity.Reply;
import Likelion.model.entity.User;
import Likelion.model.repository.CommentRepository;
import Likelion.model.repository.PostRepository;
import Likelion.model.repository.ReplyRepository;
import Likelion.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReplyService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    @Transactional
    public Reply createReply(Long user_id, Long comment_id, Reply reply){
        User user = userRepository.findById(user_id);
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("답글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + comment_id));
        Reply createReply = replyRepository.save(reply);

        reply.setUser(user);
        reply.setComment(comment);

    }
    @Transactional
    public List<Reply> findReplies(){
        return replyRepository.findAll();
    }
    @Transactional
    public void modifyReply(Long reply_id){
        Reply reply = replyRepository.findById(reply_id).orElseThrow(()->
                new IllegalArgumentException("해당 답글이 존재하지 않음" + reply_id));
        reply.updateReply(reply.getBody());
    }
    @Transactional
    public void deleteReply(Long reply_id){
        Reply reply = replyRepository.findById(reply_id).orElseThrow(()->
                new IllegalArgumentException("해당 답글 존재하지 않음 id= " + reply_id));
        replyRepository.delete(reply);
    }
}


package Likelion.service;


import Likelion.model.dto.ReplyDto;
import Likelion.model.entity.Reply;
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
public class ReplyServiceImpl {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    @Transactional
    public Reply createReply(Reply reply){
        Reply createReply = replyRepository.save(reply)
        return createReply;
    }
    @Transactional
    public List<Reply> findReplies(){
        return replyRepository.findAll();
    }
    @Transactional
    public void removeReply(Long reply_id){
        replyRepository.deleteById(reply_id);
    }
    @Transactional
    public void modify(ReplyDto replyDto){

    }
}


package Likelion.Recruiting.domain.reply.service;

import Likelion.Recruiting.domain.reply.entity.Reply;
import Likelion.Recruiting.domain.reply.entity.ReplyLike;
import Likelion.Recruiting.domain.reply.repository.ReplyLikeRepository;
import Likelion.Recruiting.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyLikeService {
    private final ReplyLikeRepository replyLikeRepository;
    //------------대댓글
    @Transactional
    public void createReplyLike(User user, Reply reply){
        ReplyLike createdReplyLike= new ReplyLike(user,reply);
        replyLikeRepository.save(createdReplyLike);

    }
    @Transactional
    public void deleteReplyLike(User user, Reply reply){
        ReplyLike replyLike = replyLikeRepository.findOneByUserAndReply(user,reply);//에러코드~~ (옵셔널임  )
        replyLike.dislike();
        replyLikeRepository.delete(replyLike);
    }
}

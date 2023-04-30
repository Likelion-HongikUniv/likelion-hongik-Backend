package Likelion.Recruiting.domain.reply.repository;


import Likelion.Recruiting.domain.reply.entity.Reply;
import Likelion.Recruiting.domain.reply.entity.ReplyLike;
import Likelion.Recruiting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike,Long> {
    ReplyLike findOneByUserAndReply(User user, Reply reply);
}

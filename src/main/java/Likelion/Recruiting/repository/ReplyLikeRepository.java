package Likelion.Recruiting.repository;


import Likelion.Recruiting.model.Reply;
import Likelion.Recruiting.model.ReplyLike;
import Likelion.Recruiting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike,Long> {
    ReplyLike findOneByUserAndReply(User user, Reply reply);
}

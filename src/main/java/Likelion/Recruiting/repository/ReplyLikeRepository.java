package Likelion.Recruiting.repository;

import Likelion.Recruiting.domain.Reply;
import Likelion.Recruiting.domain.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike,Long> {
}

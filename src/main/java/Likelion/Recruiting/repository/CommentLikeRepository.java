package Likelion.Recruiting.repository;



import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.CommentLike;
import Likelion.Recruiting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    CommentLike findOneByUserAndComment(User user, Comment comment);
}

package Likelion.Recruiting.domain.comment.repository;



import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.comment.entity.CommentLike;
import Likelion.Recruiting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    CommentLike findOneByUserAndComment(User user, Comment comment);
}

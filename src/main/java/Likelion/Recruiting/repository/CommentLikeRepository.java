package Likelion.Recruiting.repository;



import Likelion.Recruiting.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    Long countByCommentId(Long commentId);
}

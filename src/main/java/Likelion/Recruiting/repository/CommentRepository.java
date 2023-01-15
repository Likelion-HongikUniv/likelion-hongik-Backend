package Likelion.Recruiting.repository;



import Likelion.Recruiting.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Long countById(Long id);
}

package Likelion.Recruiting.repository;



import Likelion.Recruiting.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("")
    List<Comment> findByPostId(Long PostId);
}

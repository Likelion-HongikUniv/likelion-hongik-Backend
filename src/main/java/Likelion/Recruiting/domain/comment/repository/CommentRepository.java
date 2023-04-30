package Likelion.Recruiting.domain.comment.repository;



import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("SELECT p FROM Comment p join fetch p.author")
    List<Comment> findAllDesc(User user);

    List<Comment> findByAuthor(User user);

    List<Comment> findByPostId(Long PostId);
    @Query("select p from Post p join fetch p.author")
    List<Comment> findAllByPostId(Long postId);
}


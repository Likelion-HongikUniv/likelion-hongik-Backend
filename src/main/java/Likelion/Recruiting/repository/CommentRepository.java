package Likelion.Recruiting.repository;



import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.Reply;
import Likelion.Recruiting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("SELECT p FROM Comment p join fetch p.author")
    List<Comment> findAllDesc(User user);

    List<Comment> findByPostId(Long PostId);
    @Query("select p from Post p join fetch p.author")
    List<Comment> findAllByPostId(Long postId);
}


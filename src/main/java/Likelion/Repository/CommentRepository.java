package Likelion.Repository;

import Likelion.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByPostId(int postId);
}


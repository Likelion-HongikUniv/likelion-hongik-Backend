package Likelion.Recruiting.repository;

import Likelion.Recruiting.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}

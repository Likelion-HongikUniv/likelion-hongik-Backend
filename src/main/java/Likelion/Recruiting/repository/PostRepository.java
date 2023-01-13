package Likelion.Recruiting.repository;

import Likelion.Recruiting.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}

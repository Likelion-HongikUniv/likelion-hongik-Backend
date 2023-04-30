package Likelion.Recruiting.domain.post.repository;

import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.entity.PostLike;

import Likelion.Recruiting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    List<PostLike> findByUser(User user);
    PostLike findOneByUserAndPost(User user, Post post);
}

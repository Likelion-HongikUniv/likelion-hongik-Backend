package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.PostLike;

import Likelion.Recruiting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    PostLike findOneByUserAndPost(User user, Post post);
}

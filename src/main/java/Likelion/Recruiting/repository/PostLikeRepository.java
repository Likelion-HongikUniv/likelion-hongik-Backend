package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.PostLike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    Long countByPostId(Long postId);
}

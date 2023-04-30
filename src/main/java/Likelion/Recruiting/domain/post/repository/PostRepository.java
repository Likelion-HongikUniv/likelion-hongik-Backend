package Likelion.Recruiting.domain.post.repository;

import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.entity.PostLike;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.comment.entity.Comment;

import Likelion.Recruiting.domain.post.entity.enums.MainCategory;
import Likelion.Recruiting.domain.post.entity.enums.SubCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long>, PostCustomRepository {

    Page<Post> findByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory,Pageable pageable);


    Page<Post> findByMainCategoryAndSubCategoryAndAuthor_TeamId(MainCategory mainCategory, SubCategory subCategory,Long teamId,Pageable pageable);

    Page<Post> findAllByAuthor(User user, Pageable pageable);

    Page<Post> findAllByLikeUsersIn(List<PostLike> postLike, Pageable pageable);

    Page<Post> findAllByCommentsIn(List<Comment> comments, Pageable pageable);

    List<Post> findAll();

    Optional<Post> findById(Long id);
}


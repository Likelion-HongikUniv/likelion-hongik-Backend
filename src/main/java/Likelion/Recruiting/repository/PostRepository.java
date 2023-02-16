package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.PostLike;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.Comment;

import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;

import Likelion.Recruiting.repository.custom.PostCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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


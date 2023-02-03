package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.PostLike;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Reply;

import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import org.springframework.data.domain.Example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory,Pageable pageable);


    Page<Post> findAllByAuthor(User user, Pageable pageable);

    Page<Post> findAllByLikeUsersIn(List<PostLike> postLike, Pageable pageable);

    Page<Post> findByComment(List<Post> post, Pageable pageable);

    List<Post> findAll();
}


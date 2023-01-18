package Likelion.Recruiting.repository;


import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;

import Likelion.Recruiting.model.Reply;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post p join fetch p.author")
    List<Post> findByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory);
    public List<Post> findAll();

    public List<Post> findByComment(List<Comment> comments);
}

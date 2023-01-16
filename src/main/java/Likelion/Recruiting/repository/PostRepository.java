package Likelion.Recruiting.repository;


import Likelion.Recruiting.model.Post;

import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post p join fetch p.author a")
    List<Post> findByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory);
}

package Likelion.Recruiting.repository;


import Likelion.Recruiting.model.Post;

import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory);
}

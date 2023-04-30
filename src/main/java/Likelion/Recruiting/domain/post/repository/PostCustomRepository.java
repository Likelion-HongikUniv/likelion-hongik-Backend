package Likelion.Recruiting.domain.post.repository;

import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.entity.enums.MainCategory;
import Likelion.Recruiting.domain.post.entity.enums.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<Post> searchPost(MainCategory mainCategory, SubCategory subCategory, String search, Pageable pageable);
    Page<Post> searchProject(MainCategory mainCategory, SubCategory subCategory, Long teamId,String search, Pageable pageable);
}

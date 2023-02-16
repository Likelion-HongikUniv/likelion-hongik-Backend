package Likelion.Recruiting.repository.custom;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<Post> searchPost(MainCategory mainCategory, SubCategory subCategory, String search, Pageable pageable);
    Page<Post> searchProject(MainCategory mainCategory, SubCategory subCategory, Long teamId,String search, Pageable pageable);
}

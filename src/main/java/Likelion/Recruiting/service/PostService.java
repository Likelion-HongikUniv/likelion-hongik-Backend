package Likelion.Recruiting.service;


import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.PostImages;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.CommentRepository;
import Likelion.Recruiting.repository.PostLikeRepository;
import Likelion.Recruiting.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;
    @Autowired
    private final CommentRepository commentRepository;

    public Post createPost(Post post, User user, ArrayList<String> imageUrls) {
        ArrayList<PostImages> postImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            postImages.add(PostImages.builder()
                    .url(imageUrls.get(i))
                    .build());
            postImages.get(i).setPost(post);
        }
        post.setAuthor(user);
        Post createdPost = postRepository.save(post);
        return createdPost;
    }
    public Long countingPostLike(Long id){
        return postLikeRepository.countById(id);
    }
    public Long countingCommentLike(Long id){
        return commentRepository.countById(id);
    }
    public List<Post> searchCategory(MainCategory mainCategory, SubCategory subCategory){
        return postRepository.findByMainCategoryAndSubCategory(mainCategory,subCategory);
    }

//    public List<Post> findPosts() {
//        return postRepository.findAll();
//    }

//    public List<Post> findAllWithPostImages() {
//        return postRepository.findAll();
//    }
}

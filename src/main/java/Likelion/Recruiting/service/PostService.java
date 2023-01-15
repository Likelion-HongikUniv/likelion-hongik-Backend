package Likelion.Recruiting.service;


import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.*;
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
    private final CommentLikeRepository commentLikeRepository;
    @Autowired
    private final ReplyLikeRepository replyLikeRepository;

    public Post createPost(Post post, User user, List<String> imageUrls) {
        List<PostImages> postImages = new ArrayList<>();
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
        return postLikeRepository.countByPostId(id);
    }
    public Long countingCommentLike(Long id){
        return commentLikeRepository.countByCommentId(id);
    }
    public Long countingReplyLike(Long id){
        return replyLikeRepository.countByReplyId(id);
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

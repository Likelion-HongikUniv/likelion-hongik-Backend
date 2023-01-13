package Likelion.Recruiting.service;

import Likelion.Recruiting.domain.Post;
import Likelion.Recruiting.domain.User;
import Likelion.Recruiting.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(Post post) {
        Post createdPost = postRepository.save(post);
        return createdPost;
    }
    @Transactional
    public List<Post> findPosts() {
        return postRepository.findAll();
    }
    @Transactional
    public List<Post> findAllWithPostImages() {
        return postRepository.findAll();
    }
}

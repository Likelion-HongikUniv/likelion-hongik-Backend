package Likelion.Recruiting.service;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 특정 User가 작성한 게시글 조회 (by. userId)
    @Transactional(readOnly = true)
    public List<Post> findPostsByUserId(Long userId) { return postRepository.findAllByUser(userId); }

    @Transactional
    public void deletePostOne(Long postId) {

        Post findPost = postRepository.findOne(postId);

        postRepository.deletePost(findPost);
    }

    @Transactional
    public void deletePostByUser(Long userId) {
        List<Post> findPosts = postRepository.findAllByUser(userId);

        postRepository.deletePosts(findPosts);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true) // 읽기에 readOnly=ture 해주면 최적화 해준대. 쓰기에는 넣으면 안됨
    public List<Post> findPosts() {
        return postRepository.findAll();
    }



    // Post 하나를 조회
    @Transactional(readOnly = true)
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }
}

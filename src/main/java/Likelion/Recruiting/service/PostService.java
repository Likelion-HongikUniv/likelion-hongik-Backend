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

    // 게시글 전체 조회
    @Transactional(readOnly = true) // 읽기에 readOnly=ture 해주면 최적화 해준대. 쓰기에는 넣으면 안됨
    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    // 게시글 1개 조회
    @Transactional(readOnly = true)
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    // 특정 User가 작성한 게시글 조회 (by. userId)
    // fetch join 사용하여 수정 가능할 듯
    @Transactional(readOnly = true)
    public List<Post> findPostsByUserId(Long userId) { return postRepository.findAllByUser(userId); }

    // 게시글 1개 삭제
    @Transactional
    public void deletePostOne(Long postId) {

        Post findPost = postRepository.findOne(postId);

        postRepository.deletePost(findPost);
    }

    // User가 작성한 게시글 전체 삭제
    // 전체 게시글 삭제 아님
    @Transactional
    public void deletePostByUser(Long userId) {
        List<Post> findPosts = postRepository.findAllByUser(userId);

        postRepository.deletePosts(findPosts);
    }
}

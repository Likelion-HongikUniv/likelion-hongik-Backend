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

    @Transactional
    public void deletePost(Long postId) {

        Post findPost = postRepository.findOne(postId);

        postRepository.deletePost(findPost);
    }


    // 게시글 전체 조회
    @Transactional(readOnly = true) // 읽기에 readOnly=ture 해주면 최적화 해준대. 쓰기에는 넣으면 안됨
    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }
}

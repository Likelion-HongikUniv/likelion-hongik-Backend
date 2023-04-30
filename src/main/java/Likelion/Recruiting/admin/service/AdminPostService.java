package Likelion.Recruiting.admin.service;

import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.admin.repository.AdminPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPostService {

    private final AdminPostRepository adminPostRepository;

    // 게시글 전체 조회
    @Transactional(readOnly = true) // 읽기에 readOnly=ture 해주면 최적화 해준대. 쓰기에는 넣으면 안됨
    public List<Post> findPosts() {
        return adminPostRepository.findAll();
    }

    // 게시글 1개 조회
    @Transactional(readOnly = true)
    public Post findOne(Long postId) {
        return adminPostRepository.findOne(postId);
    }

    // 특정 User가 작성한 게시글 조회 (by. userId)
    // fetch join 사용하여 수정 가능할 듯
    @Transactional(readOnly = true)
    public List<Post> findPostsByUserId(Long userId) { return adminPostRepository.findAllByUser(userId); }

    // 게시글 1개 삭제
    @Transactional
    public void deletePostOne(Long postId) {

        Post findPost = adminPostRepository.findOne(postId);

        adminPostRepository.deletePost(findPost);
    }

    // User가 작성한 게시글 전체 삭제
    // 전체 게시글 삭제 아님
    @Transactional
    public void deletePostByUser(Long userId) {
        List<Post> findPosts = adminPostRepository.findAllByUser(userId);

        adminPostRepository.deletePosts(findPosts);
    }
}

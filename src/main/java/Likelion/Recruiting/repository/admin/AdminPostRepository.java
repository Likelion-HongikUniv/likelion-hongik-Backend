package Likelion.Recruiting.repository.admin;

import Likelion.Recruiting.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminPostRepository {

    private final EntityManager em;

    // post ID로 조회
    public Post findOne(Long id) { return em.find(Post.class, id); }

    // 전체 게시글 조회
    public List<Post> findAll() {
        List<Post> result = em.createQuery("select m from Post m", Post.class)
                .getResultList();

        return result;
    }

    // 작성자의 게시글 조회
    public List<Post> findAllByUser(Long userId) {
        List<Post> result = em.createQuery("select distinct p from Post p where p.author.id = :userId", Post.class)
                .setParameter("userId", userId)
                .getResultList();

        return result;
    }

    // 게시글 1개 삭제
    public void deletePost(Post post) {
        em.remove(post);
    }

    // 게시글 N개 삭제
    public void deletePosts(List<Post> posts) {
        for (Post post : posts) { em.remove(post); }
    }

}

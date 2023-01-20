package Likelion.Recruiting.repository;

import Likelion.Recruiting.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        List<Post> result = em.createQuery("select m from Post m", Post.class)
                .getResultList();

        return result;
    }

    public List<Post> findByUserId(Long userId) {
        List<Post> result = em.createQuery("select distinct p from Post p, User u where p.user.id = :userId", Post.class)
                .setParameter("userId", userId)
                .getResultList();

        return result;
    }

    public List<Post> findById(Long id) {
        return em.createQuery("select m from Post m where m.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void deletePost(Post post) {
        em.remove(post);
    }

    public void deletePosts(List<Post> posts) {
        for (Post post : posts) { em.remove(post); }
    }

}

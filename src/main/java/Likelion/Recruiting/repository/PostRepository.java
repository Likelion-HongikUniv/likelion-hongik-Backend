package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.Post;
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

    public void deletePost(Post post) {
        em.remove(post);
    }

    public List<Post> findAll() {
        List<Post> result = em.createQuery("select m from Post m", Post.class)
                .getResultList();

        return result;
    }

    public List<Post> findById(Long id) {
        return em.createQuery("select m from Post m where m.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();
    }
}

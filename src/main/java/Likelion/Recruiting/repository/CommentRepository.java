package Likelion.Recruiting.repository;

import Likelion.Recruiting.domain.Comment;
import Likelion.Recruiting.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Comment findOne(Long id) { return em.find(Comment.class, id); }

    public List<Comment> findAll() {
        List<Comment> result = em.createQuery("select c from Comment c", Comment.class)
                .getResultList();

        return result;
    }

    public List<Comment> findByUserId(Long userId) {
        List<Comment> result = em.createQuery("select distinct c from Comment c, User u where c.user.id = :userId", Comment.class)
                .setParameter("userId", userId)
                .getResultList();

        return result;
    }

    public List<Comment> findById(Long id) {
        return em.createQuery("select c from Comment c where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void deleteComment(Comment comment) { em.remove(comment); }

}






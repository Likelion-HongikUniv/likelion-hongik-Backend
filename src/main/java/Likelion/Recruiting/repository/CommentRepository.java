package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;


    // 모든 댓글 조회
    public List<Comment> findAll() {
        List<Comment> result = em.createQuery("select c from Comment c", Comment.class)
                .getResultList();

        return result;
    }

    // user_id로 찾기
    public List<Comment> findByUserId(Long userId) {
        List<Comment> result = em.createQuery("select distinct c from Comment c where c.author.id = :userId", Comment.class)
                .setParameter("userId", userId)
                .getResultList();

        return result;
    }

    // comment_id로 찾기
    public List<Comment> findById(Long id) {
        return em.createQuery("select c from Comment c where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
    }

    // comment_id로 찾기2
    public Comment findOne(Long id) { return em.find(Comment.class, id); }

    // 댓글 하나 지우기
    public void deleteComment(Comment comment) { em.remove(comment); }

    // 댓글 여러개 지우기
    public void deleteComments(List<Comment> comments) {
        for(Comment comment : comments) {
            em.remove(comment);
        }
    }


}






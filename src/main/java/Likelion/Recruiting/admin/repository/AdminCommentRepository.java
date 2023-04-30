package Likelion.Recruiting.admin.repository;

import Likelion.Recruiting.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminCommentRepository {

    private final EntityManager em;

    // comment ID로 조회
    public Comment findOne(Long id) { return em.find(Comment.class, id); }

    // 모든 댓글 조회
    public List<Comment> findAll() {
        List<Comment> result = em.createQuery("select c from Comment c", Comment.class)
                .getResultList();

        return result;
    }

    // 작성자의 댓글 조회
    public List<Comment> findByUserId(Long userId) {
        List<Comment> result = em.createQuery("select distinct c from Comment c where c.author.id = :userId", Comment.class)
                .setParameter("userId", userId)
                .getResultList();

        return result;
    }

    // 댓글 1개 삭제
    public void deleteComment(Comment comment) { em.remove(comment); }

    // 댓글 N개 삭제
    public void deleteComments(List<Comment> comments) {
        for(Comment comment : comments) {
            em.remove(comment);
        }
    }


}






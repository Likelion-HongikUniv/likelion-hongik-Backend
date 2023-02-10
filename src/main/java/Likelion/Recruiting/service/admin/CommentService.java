package Likelion.Recruiting.service.admin;

import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.repository.admin.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 특정 User가 작성한 댓글 조회 (by. userId)
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByUserId(Long userId) { return commentRepository.findByUserId(userId); }

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<Comment> findComments() {
        return commentRepository.findAll();
    }

    // 댓글 1개 조회
    @Transactional(readOnly = true)
    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId);
    }

    // 댓글 1개 삭제
    @Transactional
    public void deleteCommentOne(Long commentId) {

        Comment findComment = commentRepository.findOne(commentId);

        commentRepository.deleteComment(findComment);
    }


    // User가 작성한 댓글 전체 삭제
    // 전체 댓글 삭제 아님
    @Transactional
    public void deleteCommentByUser(Long userId) {
        List<Comment> findComments = commentRepository.findByUserId(userId);

        commentRepository.deleteComments(findComments);
    }

}

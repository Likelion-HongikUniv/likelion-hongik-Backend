package Likelion.Recruiting.admin.service;

import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.admin.repository.AdminCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCommentService {

    private final AdminCommentRepository adminCommentRepository;

    // 특정 User가 작성한 댓글 조회 (by. userId)
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByUserId(Long userId) { return adminCommentRepository.findByUserId(userId); }

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<Comment> findComments() {
        return adminCommentRepository.findAll();
    }

    // 댓글 1개 조회
    @Transactional(readOnly = true)
    public Comment findOne(Long commentId) {
        return adminCommentRepository.findOne(commentId);
    }

    // 댓글 1개 삭제
    @Transactional
    public void deleteCommentOne(Long commentId) {

        Comment findComment = adminCommentRepository.findOne(commentId);

        adminCommentRepository.deleteComment(findComment);
    }


    // User가 작성한 댓글 전체 삭제
    // 전체 댓글 삭제 아님
    @Transactional
    public void deleteCommentByUser(Long userId) {
        List<Comment> findComments = adminCommentRepository.findByUserId(userId);

        adminCommentRepository.deleteComments(findComments);
    }

}

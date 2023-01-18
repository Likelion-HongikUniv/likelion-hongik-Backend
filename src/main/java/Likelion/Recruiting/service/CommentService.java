package Likelion.Recruiting.service;

import Likelion.Recruiting.domain.Comment;
import Likelion.Recruiting.domain.Post;
import Likelion.Recruiting.repository.CommentRepository;
import Likelion.Recruiting.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 특정 User가 작성한 게시글 조회 (by. userId)
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByUserId(Long userId) { return commentRepository.findByUserId(userId); }

    @Transactional
    public void deleteComment(Long commentId) {

        Comment findComment = commentRepository.findOne(commentId);

        commentRepository.deleteComment(findComment);
    }


    // 댓글 전체 조회
    @Transactional(readOnly = true) // 읽기에 readOnly=ture 해주면 최적화 해준대. 쓰기에는 넣으면 안됨
    public List<Comment> findComments() {
        return commentRepository.findAll();
    }


    // 댓글 하나를 조회
    @Transactional(readOnly = true)
    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId);
    }
}

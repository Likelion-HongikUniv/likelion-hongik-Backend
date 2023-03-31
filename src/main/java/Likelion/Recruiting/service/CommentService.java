package Likelion.Recruiting.service;

import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.CreateCommentRequestDto;
import Likelion.Recruiting.model.dto.CreateResponseMessage;
import Likelion.Recruiting.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(Comment comment, Post post, User user){
        comment.setAuthor(user);
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        return createdComment;
    }

    @Transactional
    public CreateResponseMessage updateComment(Long commentId, Long userId, CreateCommentRequestDto reqeust){
        Comment comment = commentRepository.findById(commentId).get();
        if(userId == comment.getAuthor().getId()){
            comment.update(reqeust.getBody());
            commentRepository.save(comment);
            return new CreateResponseMessage((long)200, "업데이트 성공");
        }
        else return new CreateResponseMessage((long)403, "본인의 댓글이 아닙니다.");

    }

    @Transactional
    public CreateResponseMessage deleteComment (Long commentId, Long userId){
        Comment comment = commentRepository.findById(commentId).get();
        if(userId.equals(comment.getAuthor().getId())) {
            comment.delete();
            if (comment.getIsDeleted() == true)
                return new CreateResponseMessage((long) 200, "삭제 성공");
            else return new CreateResponseMessage((long) 404, "이미 삭제된 댓글입니다.");
        }
        else return new CreateResponseMessage((long)403, "본인의 댓글이 아닙니다.");
    }

    public List<Comment> findUser_Comment(User user) {

        return commentRepository.findAllDesc(user);
    }
}

package Likelion.Recruiting.service;

import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.CommentRepository;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Comment updateComment(Comment comment, String body){
        comment.update(body);
        return comment;
    }

    @Transactional
    public Comment deleteComment (Comment comment){
        comment.delete(); // 진짜 삭제가 아니라 불값만 변경 ==>> 객체는 있으나 프론트에서 표기만 ~~
        return comment;
    }

    public List<Comment> findUser_Comment(User user) {

        return commentRepository.findAllDesc(user);
    }
}

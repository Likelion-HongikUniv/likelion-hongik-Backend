package Likelion.Recruiting.domain.comment.service;

import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.comment.entity.CommentLike;
import Likelion.Recruiting.domain.comment.repository.CommentLikeRepository;
import Likelion.Recruiting.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    //---------------댓글
    @Transactional
    public void createCommentLike(User user, Comment comment){
        CommentLike createdCommentLike= new CommentLike(user,comment);
        commentLikeRepository.save(createdCommentLike);

    }
    @Transactional
    public void deleteCommentLike(User user, Comment comment){
        CommentLike commentLike = commentLikeRepository.findOneByUserAndComment(user,comment);//에러코드~~ (옵셔널임  )
        commentLike.dislike();
        commentLikeRepository.delete(commentLike);
    }
}


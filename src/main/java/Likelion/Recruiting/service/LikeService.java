package Likelion.Recruiting.service;

import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final ReplyRepository replyRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;
    @Autowired
    private final CommentLikeRepository commentLikeRepository;
    @Autowired
    private final ReplyLikeRepository replyLikeRepository;
    //---------------게시글 좋아요
    @Transactional
    public void createPostLike(User user, Post post){
        PostLike createdPostLike= new PostLike(user,post);
        postLikeRepository.save(createdPostLike);
    }
    @Transactional
    public void deletePostLike(User user, Post post){
        PostLike postLike = postLikeRepository.findOneByUserAndPost(user,post);//에러코드~~ (옵셔널임  )
        postLike.dislike();
        postLikeRepository.delete(postLike);
    }

    //---------------댓글
    @Transactional
    public void createCommentLike(User user, Comment comment){
        CommentLike createdCommentLike= new CommentLike(user,comment);
        commentLikeRepository.save(createdCommentLike);

    }
    
    public Page<PostDto> getLikedPost(Long userId, Pageable pageable){

        User user = userRepository.findById(userId).get();
        List<PostLike> postLikes = postLikeRepository.findByUser(user);
        Page<Post> posts = postRepository.findAllByLikeUsersIn(postLikes, pageable);
        Page<PostDto> postDto = posts.map(p -> PostDto.builder()
                .postId(p.getId())
                .title(p.getTitle())
                .author(p.getAuthor().getName())
                .time(p.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                .body(p.getBody())
                .likes(p.getLikeUsers().size())
                .comments(p.getComments().size())
                .build());


        return postDto;
    }

    
    @Transactional
    public void deleteCommentLike(User user, Comment comment){
        CommentLike commentLike = commentLikeRepository.findOneByUserAndComment(user,comment);//에러코드~~ (옵셔널임  )
        commentLike.dislike();
        commentLikeRepository.delete(commentLike);
    }
    //------------대댓글
    @Transactional
    public void createReplyLike(User user, Reply reply){
        ReplyLike createdReplyLike= new ReplyLike(user,reply);
        replyLikeRepository.save(createdReplyLike);

    }
    @Transactional
    public void deleteReplyLike(User user, Reply reply){
        ReplyLike replyLike = replyLikeRepository.findOneByUserAndReply(user,reply);//에러코드~~ (옵셔널임  )
        replyLike.dislike();
        replyLikeRepository.delete(replyLike);
    }


}

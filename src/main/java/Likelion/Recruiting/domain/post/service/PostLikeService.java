package Likelion.Recruiting.domain.post.service;


import Likelion.Recruiting.domain.post.dto.MypagePostDto;
import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.entity.PostLike;
import Likelion.Recruiting.domain.post.repository.PostLikeRepository;
import Likelion.Recruiting.domain.post.repository.PostRepository;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
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
    // ------------------------- 내가 좋아요 누른 게시글 가져오기 ------------------------- //
    public Page<MypagePostDto> getLikedPost(Long userId, Pageable pageable){

        User user = userRepository.findById(userId).get();
        List<PostLike> postLikes = postLikeRepository.findByUser(user);
        Page<Post> posts = postRepository.findAllByLikeUsersIn(postLikes, pageable);
        Page<MypagePostDto> postDto = posts.map(p -> MypagePostDto.builder()
                .postId(p.getId())
                .title(p.getTitle())
                .author(p.getAuthor().getNickname())
                .authorImage(p.getAuthor().getProfileImage())
                .time(p.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                .body(p.getBody())
                .likes(p.getLikeUsers().size())
                .comments(p.getComments().size())
                .build());
        return postDto;
    }
}


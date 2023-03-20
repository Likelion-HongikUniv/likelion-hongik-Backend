package Likelion.Recruiting.service;


import Likelion.Recruiting.controller.CommunityController;
import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.*;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.*;
import lombok.RequiredArgsConstructor;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public Post createPost(Post post, User user) {
        post.setAuthor(user);
        Post createdPost = postRepository.save(post);
        return createdPost;
    }
    @Transactional
    public CreateResponseMessage updatePost(Long postId,Long userId, PostUpdateDto postUpdateDto){
        Post post = postRepository.findById(postId).get();
        if(userId == post.getAuthor().getId()){
            post.update(postUpdateDto);
            postRepository.save(post);
            return new CreateResponseMessage((long)200, "업데이트 성공");
        }
        else return new CreateResponseMessage((long)403, "본인의 게시글이 아닙니다.");

    }
    @Transactional
    public CreateResponseMessage deletePost(Long postId,Long userId){
        Post post = postRepository.findById(postId).get();
        if(userId == post.getAuthor().getId()) {
            postRepository.delete(post);
            return new CreateResponseMessage((long) 200, "업데이트 성공");
        }
        else return new CreateResponseMessage((long)403, "본인의 게시글이 아닙니다.");
    }

    public Post findPost(Long postId){
        return postRepository.findById(postId).get();
    }

    public PostDetailDto detailPost(Long postId, String email){
        Post post = postRepository.findById(postId).get();
        User user = userRepository.findByEmail(email).get();

        return new PostDetailDto(post, user);
    }

    public PageResponseDto<PostSimpleDto> findPostByCategory(MainCategory mainCategory, SubCategory subCategory, User user, Pageable pageable){
        Page<Post> posts = postRepository.findByMainCategoryAndSubCategory(mainCategory,subCategory,pageable);
        Page<PostSimpleDto> result = posts.map(p-> new PostSimpleDto(p,user));
        return new PageResponseDto<PostSimpleDto>(result);
    }
    public PageResponseDto<PostSimpleDto> findProjectByCategory(MainCategory mainCategory, SubCategory subCategory, Long teamId, User user, Pageable pageable){
        Page<Post> posts= postRepository.findByMainCategoryAndSubCategoryAndAuthor_TeamId(mainCategory,subCategory,teamId,pageable);
        Page<PostSimpleDto> result = posts.map(p-> new PostSimpleDto(p,user));
        return new PageResponseDto<PostSimpleDto>(result);
    }

    public PageResponseDto<PostSimpleDto> searchPost(MainCategory mainCategory, SubCategory subCategory, User user,String search, Pageable pageable){
        Page<Post> posts = postRepository.searchPost(mainCategory,subCategory,search,pageable);
        Page<PostSimpleDto> result = posts.map(p-> new PostSimpleDto(p,user));
        return new PageResponseDto<PostSimpleDto>(result);
    }

    public PageResponseDto<PostSimpleDto> searchProject(MainCategory mainCategory, SubCategory subCategory, Long teamId, User user,String search, Pageable pageable){
        Page<Post> posts= postRepository.searchProject(mainCategory,subCategory,teamId,search,pageable);
        Page<PostSimpleDto> result = posts.map(p-> new PostSimpleDto(p,user));
        return new PageResponseDto<PostSimpleDto>(result);
    }
    public PostDetailDto postDetailInfo(Long postId,User user){
        Post post = postRepository.findById(postId).get();
        PostDetailDto result = new PostDetailDto(post, user);
        return result;
    }

    public Post searchOneId(Long id) {
        return postRepository.findById(id).get();
    }

// ------------------------- 내가 쓴 게시글 가져오기 ------------------------- //
    public Page<MypagePostDto> getMyAllPost(String email, Pageable pageable){
        // 해당 이메일가진 User 객체 가져오기
        User user = userRepository.findByEmail(email).get();

        Page<Post> posts = postRepository.findAllByAuthor(user, pageable);

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

// ------------------------- 내가 쓴 댓글이 있는 게시글 가져오기 ------------------------- //
    public Page<MypagePostDto> getPosts(Long userId, Pageable pageable) {

        User user = userRepository.findById(userId).get();
        List<Comment> comments = commentRepository.findByAuthor(user);
        Page<Post> postsByComment = postRepository.findAllByCommentsIn(comments, pageable);

        Page<MypagePostDto> result = postsByComment.map(p -> MypagePostDto.builder()
                        .postId(p.getId())
                        .title(p.getTitle())
                        .author(p.getAuthor().getNickname())
                        .authorImage(p.getAuthor().getProfileImage())
                        .time(p.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                        .body(p.getBody())
                        .likes(p.getLikeUsers().size())
                        .comments(p.getComments().size())
                        .build());

        return result;
    }

    public List<Post> findPostAll(){
        return postRepository.findAll();
    }

    public List<Post> findByComment(List<Comment> comments){
        List<Post> posts = comments.stream()
                .map(c->c.getPost())
                .collect(Collectors.toList());
        return posts;
    }


}

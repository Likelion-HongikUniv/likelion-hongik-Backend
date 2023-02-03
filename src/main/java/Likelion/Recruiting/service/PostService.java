package Likelion.Recruiting.service;


import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.dto.PostSimpleDto;
import Likelion.Recruiting.model.dto.PostUpdateDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

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
    public Post updatePost(Post post, PostUpdateDto postUpdateDto){
        post.update(postUpdateDto);
        return postRepository.save(post);
    }
    @Transactional
    public void deletePost(Post post){
        Post deletePost = postRepository.findById(post.getId()).get();
        postRepository.delete(deletePost);
    }

    public Post findPost(Long postId){
        return postRepository.findById(postId).get();
    }


    public Page<Post> searchCategory(MainCategory mainCategory, SubCategory subCategory,Pageable pageable){
        return postRepository.findByMainCategoryAndSubCategory(mainCategory,subCategory,pageable);
    }
    public Page<Post> searchProject(MainCategory mainCategory, SubCategory subCategory, Long teamId,Pageable pageable){
        return postRepository.findByMainCategoryAndSubCategoryAndAuthor_TeamId(mainCategory,subCategory,teamId,pageable);
    }

    public Post searchOneId(Long id) {
        return postRepository.findById(id).get();
    }


    public Page<PostDto> getMyAllPost(String email, Pageable pageable){
        // 해당 이메일가진 User 객체 가져오기
        User user = userRepository.findByEmail(email).get();

        Page<Post> posts = postRepository.findAllByAuthor(user, pageable);

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

    public Page<PostDto> getPosts(Long userId, Pageable pageable) {

        User user = userRepository.findById(userId).get();
        List<Comment> comments = commentRepository.findByAuthor(user);
        Page<Post> postsByComment = postRepository.findAllByCommentsIn(comments, pageable);

        Page<PostDto> result = postsByComment.map(p -> PostDto.builder()
                        .postId(p.getId())
                        .title(p.getTitle())
                        .author(p.getAuthor().getName())
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

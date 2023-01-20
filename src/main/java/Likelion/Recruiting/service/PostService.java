package Likelion.Recruiting.service;


import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.dto.PostSimpleDto;
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

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post createPost(Post post, User user, List<String> imageUrls) {
        List<PostImages> postImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            postImages.add(PostImages.builder()
                    .url(imageUrls.get(i))
                    .build());
            postImages.get(i).setPost(post);
        }
        post.setAuthor(user);
        Post createdPost = postRepository.save(post);
        return createdPost;
    }


    public List<Post> searchCategory(MainCategory mainCategory, SubCategory subCategory){
        return postRepository.findByMainCategoryAndSubCategory(mainCategory,subCategory);
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
                .profileImage(user.getProfileImage())
                .time(p.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                .body(p.getBody())
                .likes(p.getLikeUsers().size())
                .comments(p.getComments().size())
                .build());
        return postDto;
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

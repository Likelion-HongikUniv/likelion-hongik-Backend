package Likelion.Recruiting.service;


import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.PostDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;
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

        public List<PostDto> getMyAllPost(String email){
            // 해당 이메일가진 User 객체 가져오기
            User user = userRepository.findByEmail(email).get();

            List<Post> posts = user.getPosts();

            List<PostDto> postDto = posts.stream()
                    .map(p -> new PostDto(p.getTitle(), p.getAuthor().getName(), user.getProfileImage(), p.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")), p.getBody(), p.getLikeUsers().size(), p.getComments().size()))
                    .collect(Collectors.toList());
            return postDto;
        }
}

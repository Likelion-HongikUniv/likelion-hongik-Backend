package Likelion.Recruiting.controller;


import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostSimpleDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.PostLikeRepository;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.service.PostImagesService;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.UserService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final UserService userService;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;

    @GetMapping("/community/posts/{mainCategory}/{subCategory}")
    public Result getPosts(@RequestHeader("HEADER") String header,@PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {
        List<Post> posts = postService.searchCategory(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory));
        List<PostSimpleDto> result = posts.stream()
                .map(p -> new PostSimpleDto(p,postService.countingPostLike(p.getId()),postService.countingCommentLike(p.getId())))
                .collect(toList());
       return new Result(result.size(), result);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("/community/posts/{mainCategory}/{subCategory}")
    public CreatePostResponse savePost(@RequestHeader("HEADER") String header, @RequestBody CreatePostRequest request, @PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {

        CreatePostRequest createPostRequest = new CreatePostRequest(request.getTitle(), request.getBody(), MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory),request.getImageUrls());
        Post createdPost = createPostRequest.toEntity();
        // user insert partition
        Long id = Long.valueOf(1);
        User user = userRepository.findById(id).get();
        Post savedPost = postService.createPost(createdPost,user,request.getImageUrls() );

        return new CreatePostResponse(savedPost.getId());
    }

    @Data
    static class CreatePostRequest {
        private String title;
        private String body;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private ArrayList<String> imageUrls= new ArrayList<>();

        public CreatePostRequest(String title, String body, MainCategory mainCategory, SubCategory subCategory, ArrayList<String> imageUrls) {
            this.title = title;
            this.body = body;
            this.mainCategory = mainCategory;
            this.subCategory = subCategory;
            this.imageUrls = imageUrls;
        }


        public Post toEntity() {
            Post post = Post.builder()
                    .title(title)
                    .body(body)
                    .mainCategory(this.getMainCategory())
                    .subCategory(this.getSubCategory())
                    .build();
            return post;
        }
    }

    @Data
    static class CreatePostResponse {
        private Long id;

        public CreatePostResponse(Long id) {
            this.id = id;
        }
    }
}



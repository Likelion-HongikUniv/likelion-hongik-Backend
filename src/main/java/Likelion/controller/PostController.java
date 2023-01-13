package Likelion.controller;


import Likelion.model.dto.PostSimpleDto;
import Likelion.model.entity.Post;
import Likelion.model.entity.enums.MainCategory;
import Likelion.model.entity.enums.SubCategory;
import Likelion.model.repository.PostRepository;
import Likelion.service.PostImagesService;
import Likelion.service.PostService;
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
    private final PostRepository postRepository;
    private final PostService postService;
    private final PostImagesService postImagesService;

    @GetMapping("/community/posts/{mainCategory}/{subCategory}/")
    public List<PostSimpleDto> getPosts() {
        List<Post> posts = postService.findPosts();
        List<PostSimpleDto> result = posts.stream()
                .map(o -> new Post(o))
                .collect(toList());
       return result;
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

    @PostMapping("/community/posts/{mainCategory}/{subCategory}/")
    public CreatePostResponse savePost(@RequestHeader("HEADER") String header, @RequestBody CreatePostRequest request, @PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {

        CreatePostRequest createPostRequest = new CreatePostRequest(request.getTitle(), request.getBody(), MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory),request.getImageUrls());
        Post createdPost = createPostRequest.toEntity();
        // user insert partition
        for (int i = 0; i < createPostRequest.getImageUrls().toArray().length; i++) {
            postImagesService.PutPostImages(createdPost, createPostRequest.getImageUrls().get(i));
        }
        Post savedPost = postService.createPost(createdPost);

        return new CreatePostResponse(savedPost.getId());
    }

    @Data
    static class CreatePostRequest {
        private String title;
        private String body;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private ArrayList<String> imageUrls;

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
                    .createdTime(LocalDateTime.now())
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



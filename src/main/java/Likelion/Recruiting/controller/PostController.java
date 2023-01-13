package Likelion.Recruiting.controller;

import Likelion.Recruiting.domain.Post;
import Likelion.Recruiting.domain.PostImages;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.service.Dto.PostSimpleDto;
import Likelion.Recruiting.service.PostImagesService;
import Likelion.Recruiting.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        List<Post> posts = PostRepository.findAll();
//        List<PostSimpleDto> result = orders.stream()
//                .map(o -> new OrderDto(o))
//                .collect(toList());
//        return result;
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
    public CreatePostResponse savePost(@RequestHeader("HEADER") String header, @RequestBody CreatePostRequest request,@PathVariable("mainCategory") String mainCategory,@PathVariable("subCategory") String subCategory){

        Post post = new Post(request.getTitle(),request.getBody(),mainCategory,subCategory);
        ArrayList<String> imageUrls = request.getImageUrls();
        Post createdPost = postService.createPost(post);
        //set user해야함
        for(int i= 0; i<imageUrls.toArray().length; i ++ ){
            postImagesService.PutPostImages(createdPost,imageUrls.get(i));
        }

        Post savedPost = postService.createPost(createdPost);

        return new CreatePostResponse(savedPost.getId());
    }
    @Data
    static  class CreatePostRequest {
        private String title;
        private String body;
        private ArrayList<String> imageUrls;
    }
    @Data
    static class CreatePostResponse {
        private Long id;
        public CreatePostResponse(Long id) {
            this.id = id;
        }
    }



}

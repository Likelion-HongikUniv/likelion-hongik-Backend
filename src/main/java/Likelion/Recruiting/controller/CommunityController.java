package Likelion.Recruiting.controller;


import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.dto.PostSimpleDto;
import Likelion.Recruiting.model.enums.LType;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.PostLikeRepository;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.UserService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final PostService postService;

    private final UserService userService;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;
    //-------------------야매 유저생성-------------
    @PostMapping("/makeUser")
    public CreatePostResponse makeUser(@RequestBody CreateUserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .lType(LType.KAKAO)
                .role(Role.USER)
                .build();
        user.profileUpdate(request.getNickname(), request.getMajor(), request.getStudentId(), request.getPart(), request.getPhoneNum());
        User savedUser = userRepository.save(user);
        return new CreatePostResponse(savedUser.getId());
    }

    @Data
    static class CreateUserRequest{
        private String name;
        private String email;
        private String profileImage;
        private String nickname;
        private String major;
        private String studentId;
        private String part;
        private String phoneNum;

        public CreateUserRequest(String name, String email, String profileImage, String nickname, String major, String studentId, String part, String phoneNum) {
            this.name = name;
            this.email = email;
            this.profileImage = profileImage;
            this.nickname = nickname;
            this.major = major;
            this.studentId = studentId;
            this.part = part;
            this.phoneNum = phoneNum;
        }
    }
    //--------------------------------------------------------
    @GetMapping("/community/posts/{mainCategory}/{subCategory}")//카테고리에따른 게시글 가져오는 api
    public Result getSimplePosts(@RequestHeader("HEADER") String header,@PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {
        List<Post> posts = postService.searchCategory(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory));
        //System.out.println(posts.stream().map(m ->));
        Long id = Long.valueOf(1);
        User user = userRepository.findById(id).get();
        List<PostSimpleDto> result = posts.stream()
                .map(p -> new PostSimpleDto(p,user))
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
//-------------------------------------------------------------------------------------------------------
    @PostMapping("/community/posts/{mainCategory}/{subCategory}")//카테고리에따른 게시글 저장 api
    public CreatePostResponse savePost(@RequestHeader("HEADER") String header, @RequestBody CreatePostRequest request, @PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {

        CreatePostRequest createPostRequest = new CreatePostRequest(request.getTitle(), request.getBody(),request.getImageUrls());
        Post createdPost = Post.builder()
                .title(createPostRequest.getTitle())
                .body(createPostRequest.getBody())
                .mainCategory(MainCategory.valueOf(mainCategory))
                .subCategory(SubCategory.valueOf(subCategory))
                .build();
        // user insert partition
        Long id = Long.valueOf(1);
        User user = userRepository.findById(id).get();
        Post savedPost = postService.createPost(createdPost,user,createPostRequest.getImageUrls());

        return new CreatePostResponse(savedPost.getId());
    }

    @Data
    static class CreatePostRequest {
        private String title;
        private String body;
        private List<String> imageUrls= new ArrayList<>();

        public CreatePostRequest(String title, String body,List<String> imageUrls) {
            this.title = title;
            this.body = body;
            this.imageUrls = imageUrls;
        }
    }

    @Data
    static class CreatePostResponse {
        private Long id;

        public CreatePostResponse(Long id) {
            this.id = id;
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/community/post/{postId}")//게시글 상세보기
    public PostDetailDto getPostDetail(@RequestHeader("HEADER") String header, @PathVariable("postId") Long postId) {
        Post post = postService.searchOneId(postId);
        System.out.println(123);
        // user insert partition
        Long id = Long.valueOf(1);
        User user = userRepository.findById(id).get();
        System.out.println(123);
        PostDetailDto result = new PostDetailDto(post, user);
//        result.setLikeCount(postService.countingPostLike(result.getId()));
//        for(int i = 0; i<result.getComments().size();i++){
//            result.getComments().get(i)
//                    .setLikeCount(postService.countingCommentLike(result.getComments().get(i).getId()));
//            for(int k = 0; k<result.getComments().get(i).getReplies().size(); k++){
//                result.getComments().get(i).getReplies().get(k)
//                        .setLikeCount(postService.countingReplyLike(result.getComments().get(i).getReplies().get(k).getId()));
//            }
//        }
        return result;
    }
}



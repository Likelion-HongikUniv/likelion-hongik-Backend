package Likelion.Recruiting.controller;


import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.Reply;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.PostDetailDto;
import Likelion.Recruiting.model.dto.PostSimpleDto;
import Likelion.Recruiting.model.enums.LType;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.*;
import Likelion.Recruiting.service.CommentService;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.ReplyService;
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

    private final CommentService commentService;
    private final ReplyService replyService;
    private final UserService userService;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final ReplyRepository replyRepository;
    @Autowired
    private final UserRepository userRepository;

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
        Long id = Long.valueOf(1);
        User user = userRepository.findById(id).get(); // 옵셔널이므로 id없을시 예외처리할때 예외코드날아감 -->try catch쓰기
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
        return result;
    }


    //------------------------------------댓글------------------------
    @PostMapping("/community/post/{postId}")//댓글 저장 api
    public CreatePostResponse saveComment(@RequestHeader("HEADER") String header, @RequestBody CreateCommentReqeust request, @PathVariable("postId") Long postId) {
        CreateCommentReqeust createCommentReqeust = new CreateCommentReqeust(request.body, request.name);
        Comment createdComment = Comment.builder()
                .body(request.getBody())
                .build();
        // user insert partition
        Long id = Long.valueOf(1);
        User user = userRepository.findById(id).get();
        Post post = postRepository.findById(postId).get();//역시 예외처리는 예외코드로
        Comment savedComment = commentService.createComment(createdComment,post,user);
        return new CreatePostResponse(savedComment.getId());
    }
    @PatchMapping("/community/comment/{commentId}") //예외처리~~~
    public CreatePatchResponse updateComment(@RequestHeader("HEADER") String header,@RequestBody CreateCommentReqeust request, @PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findById(commentId).get();//예외처리
        Comment updatedComment = commentService.updateComment(comment, request.body);
        return new CreatePatchResponse((long)200, "업데이트 성공");
    }
    @DeleteMapping("/community/comment/{commentId}") //예외처리~~~
    public CreatePatchResponse updateComment(@RequestHeader("HEADER") String header,@PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findById(commentId).get();//예외처리
        Comment deletedComment = commentService.deleteComment(comment);
        if (deletedComment.getIsDeleted() == true)
            return new CreatePatchResponse((long)200, "업데이트 성공");
        else return new CreatePatchResponse((long)404, "업데이트 실패");
    }
    @Data
    static class CreateCommentReqeust {
        private String body;
        private String name;


        public CreateCommentReqeust(String body, String name) {
            this.body = body;
            this.name = name;
        }
    }
    //------------------------------------대댓글------------------------
    @PostMapping("/community/comment/{commentId}")//대댓글 저장 api
    public CreatePostResponse saveReply(@RequestHeader("HEADER") String header, @RequestBody CreateCommentReqeust request, @PathVariable("commentId") Long commentId) {
        Reply createdReply = Reply.builder()
                .body(request.getBody())
                .build();
        // user insert partition
        Long id = Long.valueOf(1);
        User user = userRepository.findById(id).get();
        Comment comment = commentRepository.findById(commentId).get();//역시 예외처리는 예외코드로
        Reply savedReply = replyService.createReply(createdReply,comment,user);
        return new CreatePostResponse(savedReply.getId());
    }
    @PatchMapping("/community/reply/{replyId}") //예외처리~~~
    public CreatePatchResponse updateReply(@RequestHeader("HEADER") String header, @RequestBody CreateCommentReqeust request, @PathVariable("replyId") Long replyId){
        Reply reply = replyRepository.findById(replyId).get();//예외처리
        Reply updatedReply = replyService.updateReply(reply, request.body);
        return new CreatePatchResponse((long)200, "업데이트 성공");//예외처리..
    }
    @DeleteMapping("/community/reply/{replyId}") //예외처리~~~
    public CreatePatchResponse updateReply(@RequestHeader("HEADER") String header,@PathVariable("replyId") Long replyId){
        Reply reply = replyRepository.findById(replyId).get();//예외처리
        Reply deletedReply= replyService.deleteReply(reply);
        if (deletedReply.getIsDeleted() == true)
            return new CreatePatchResponse((long)200, "업데이트 성공");
        else return new CreatePatchResponse((long)404, "업데이트 실패");
    }


    @Data
    static class CreatePatchResponse{
        private Long responseCode;
        private String message;

        public CreatePatchResponse(Long responseCode, String message) {
            this.responseCode = responseCode;
            this.message = message;
        }
    }
}



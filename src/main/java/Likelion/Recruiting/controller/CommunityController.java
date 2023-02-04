package Likelion.Recruiting.controller;


import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.model.*;
import Likelion.Recruiting.model.dto.*;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.*;
import Likelion.Recruiting.service.*;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final PostService postService;

    private final CommentService commentService;
    private final ReplyService replyService;
    private final LikeService likeService;
    private final UserService userService;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final ReplyRepository replyRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;
    @Autowired
    private final CommentLikeRepository commentLikeRepository;
    @Autowired
    private final ReplyLikeRepository replyLikeRepository;



    //-------------------야매 유저생성-------------
//    @PostMapping("/makeUser")
//    public CreatePostResponse makeUser(@RequestBody CreateUserRequest request) {
//        User user = User.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .lType(LType.KAKAO)
//                .role(Role.USER)
//                .build();
//        user.profileUpdate(request.getNickname(), request.getMajor(), request.getStudentId(), request.getPart(), request.getPhoneNum());
//        User savedUser = userRepository.save(user);
//        return new CreatePostResponse(savedUser.getId());
//    }
//
//    @Data
//    static class CreateUserRequest{
//        private String name;
//        private String email;
//        private String profileImage;
//        private String nickname;
//        private String major;
//        private String studentId;
//        private String part;
//        private String phoneNum;
//
//        public CreateUserRequest(String name, String email, String profileImage, String nickname, String major, String studentId, String part, String phoneNum) {
//            this.name = name;
//            this.email = email;
//            this.profileImage = profileImage;
//            this.nickname = nickname;
//            this.major = major;
//            this.studentId = studentId;
//            this.part = part;
//            this.phoneNum = phoneNum;
//        }
//    }
    //--------------------------------------------------------

//    @GetMapping("/community/posts/{mainCategory}/{subCategory}")//카테고리에따른 게시글 가져오는 api
//    public DataResponseDto getSimplePosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestHeader("HEADER") String header, @PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {
//        List<Post> posts = postService.searchCategory(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory));
//        String email = customOauthUser.getUser().getEmail();
//
//        User user = userService.findUser(email); // 옵셔널이므로 id없을시 예외처리할때 예외코드날아감 -->try catch쓰기
//        List<PostSimpleDto> result = posts.stream()
//                .map(p -> new PostSimpleDto(p,user))
//                .collect(toList());
//       return new DataResponseDto(result.size(), result);
//    }

    @GetMapping("/community/posts/{mainCategory}/{subCategory}")//카테고리에따른 게시글 가져오는 api
    public PageResponseDto<PostSimpleDto> getSimplePosts(
                                          @AuthenticationPrincipal CustomOauthUserImpl customOauthUser,
                                          @PageableDefault(page =1,size=5, sort="createdTime" ,direction = Sort.Direction.DESC)Pageable pageable,
                                          @PathVariable("mainCategory") String mainCategory,
                                          @PathVariable("subCategory") String subCategory) {
        Page<Post> posts = postService.searchCategory(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory),pageable);
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        Page<PostSimpleDto> result = posts.map(p-> new PostSimpleDto(p,user));
        return new PageResponseDto<PostSimpleDto>(result);
    }





    //-------------------------------------------------------------------------------------------------------
    //카테고리에따른 게시글 저장 api
    @PostMapping("/community/posts/{mainCategory}/{subCategory}")
    public CreatePostResponse savePost(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestHeader("HEADER") String header, @RequestBody CreatePostRequest request, @PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {

        CreatePostRequest createPostRequest = new CreatePostRequest(request.getTitle(), request.getBody(),request.getThumbnailImage());
        Post createdPost = Post.builder()
                .title(createPostRequest.getTitle())
                .body(createPostRequest.getBody())
                .thumbnailImage(createPostRequest.getThumbnailImage())
                .mainCategory(MainCategory.valueOf(mainCategory))
                .subCategory(SubCategory.valueOf(subCategory))
                .build();
        // user insert partition
        String email = customOauthUser.getUser().getEmail();
        //test
        User user = userService.findUser(email);
        Post savedPost = postService.createPost(createdPost,user);

        return new CreatePostResponse(savedPost.getId());
    }

    @Data
    static class CreatePostRequest {
        private String title;
        private String body;
        private String thumbnailImage;

        public CreatePostRequest(String title, String body,String thumbnailImage) {
            this.title = title;
            this.body = body;
            this.thumbnailImage = thumbnailImage;
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
    public PostDetailDto getPostDetail(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@PathVariable("postId") Long postId) {
//        Post post = postService.searchOneId(postId);
        String email = customOauthUser.getUser().getEmail();
//        User user = userService.findUser(email);

        return postService.detailPost(postId, email);
//        PostDetailDto result = new PostDetailDto(post, user);
//        return result;
    }
    //---------------------------------------------------------------

    @PostMapping("/community/post/{postId}/like") // 게시글좋아용(이미 있으면 삭제, 없으면 저장)
    public CreateResponeseMessage likePost(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@PathVariable("postId") Long postId){
        Post post = postRepository.findById(postId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        PostLike postLike = postLikeRepository.findOneByUserAndPost(user,post);
        if (postLike == null){
            likeService.createPostLike(user,post);
        }
        else likeService.deletePostLike(user,post);

        return new CreateResponeseMessage((long)200, "좋아요 성공");
    }

    //------------------------------------댓글------------------------

    @GetMapping("/community/post/{postId}/comments")//게시글에 따른 댓글 & 대댓글 불러오기
    public DataResponseDto getSimplePosts(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestHeader("HEADER") String header, @PathVariable("postId") Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);// 옵셔널이므로 id없을시 예외처리할때 예외코드날아감 -->try catch쓰기
        List<CommentDto> result = comments.stream()
                .map(comment -> new CommentDto(comment,user))
                .collect(Collectors.toList());
        return new DataResponseDto(result.size(), result);
    }
    @PostMapping("/community/post/{postId}")//댓글 저장 api
    public CreatePostResponse saveComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreateCommentReqeust request, @PathVariable("postId") Long postId) {
        Comment createdComment = Comment.builder()
                .body(request.getBody())
                .build();
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        Post post = postRepository.findById(postId).get();//역시 예외처리는 예외코드로
        Comment savedComment = commentService.createComment(createdComment,post,user);
        return new CreatePostResponse(savedComment.getId());
    }
    @PatchMapping("/community/comment/{commentId}") //예외처리~~~
    public CreateResponeseMessage updateComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@RequestBody CreateCommentReqeust request, @PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findById(commentId).get();//예외처리
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        if(user.getId() == comment.getAuthor().getId()){
            Comment updatedComment = commentService.updateComment(comment, request.body);
            return new CreateResponeseMessage((long)200, "업데이트 성공");
        }
        else return new CreateResponeseMessage((long)403, "본인의 댓글이 아닙니다.");
    }
    @DeleteMapping("/community/comment/{commentId}") //예외처리~~~
    public CreateResponeseMessage updateComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findById(commentId).get();//예외처리
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        if(user.getId() == comment.getAuthor().getId()) {
            Comment deletedComment = commentService.deleteComment(comment);
            if (deletedComment.getIsDeleted() == true)
                return new CreateResponeseMessage((long) 200, "업데이트 성공");
            else return new CreateResponeseMessage((long) 404, "업데이트 실패");
        }
        else return new CreateResponeseMessage((long)403, "본인의 댓글이 아닙니다.");
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class CreateCommentReqeust {
        private String body;

    }

    @PostMapping("/community/comment/{commentId}/like") // 게시글좋아용(이미 있으면 삭제, 없으면 저장)
    public CreateResponeseMessage likeComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findById(commentId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);//   유저는 헤더파일에서 뽑아오기
        CommentLike commentLike = commentLikeRepository.findOneByUserAndComment(user,comment);
        if (commentLike == null){
            likeService.createCommentLike(user,comment);
        }
        else likeService.deleteCommentLike(user,comment);

        return new CreateResponeseMessage((long)200, "좋아요 성공");
    }


    //------------------------------------대댓글------------------------
    @PostMapping("/community/comment/{commentId}")//대댓글 저장 api
    public CreatePostResponse saveReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestHeader("HEADER") String header, @RequestBody CreateCommentReqeust request, @PathVariable("commentId") Long commentId) {
        Reply createdReply = Reply.builder()
                .body(request.getBody())
                .build();
        // user insert partition
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        Comment comment = commentRepository.findById(commentId).get();//역시 예외처리는 예외코드로
        Reply savedReply = replyService.createReply(createdReply,comment,user);
        return new CreatePostResponse(savedReply.getId());
    }
    @PatchMapping("/community/reply/{replyId}") //예외처리~~~
    public CreateResponeseMessage updateReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreateCommentReqeust request, @PathVariable("replyId") Long replyId){
        Reply reply = replyRepository.findById(replyId).get();//예외처리
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        if(user.getId() == reply.getAuthor().getId()) {
            Reply updatedReply = replyService.updateReply(reply, request.body);
            return new CreateResponeseMessage((long) 200, "업데이트 성공");//예외처리..
        }
        else return new CreateResponeseMessage((long)403, "본인의 댓글이 아닙니다.");
    }
    @DeleteMapping("/community/reply/{replyId}") //예외처리~~~
    public CreateResponeseMessage updateReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("replyId") Long replyId){
        Reply reply = replyRepository.findById(replyId).get();//예외처리
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        if(user.getId() == reply.getAuthor().getId())
            return new CreateResponeseMessage((long)403, "본인의 댓글이 아닙니다.");
        Reply deletedReply= replyService.deleteReply(reply);
        if (deletedReply.getIsDeleted() == true)
            return new CreateResponeseMessage((long)200, "업데이트 성공");
        else return new CreateResponeseMessage((long)404, "업데이트 실패");
    }

    @PostMapping("/community/reply/{replyId}/like") // 게시글좋아용(이미 있으면 삭제, 없으면 저장)
    public CreateResponeseMessage likeReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("replyId") Long replyId){
        Reply reply= replyRepository.findById(replyId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email); //   유저는 헤더파일에서 뽑아오기
        ReplyLike replyLike = replyLikeRepository.findOneByUserAndReply(user,reply);
        if (replyLike == null){
            likeService.createReplyLike(user,reply);
        }
        else likeService.deleteReplyLike(user,reply);
        return new CreateResponeseMessage((long)200, "좋아요 성공");
    }


    @Data
    static class CreateResponeseMessage {
        private Long responseCode;
        private String message;

        public CreateResponeseMessage(Long responseCode, String message) {
            this.responseCode = responseCode;
            this.message = message;
        }

    }
}


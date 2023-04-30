package Likelion.Recruiting.controller;


import Likelion.Recruiting.common.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.common.dto.CreateResponseMessage;
import Likelion.Recruiting.common.dto.DataResponseDto;
import Likelion.Recruiting.common.dto.PageResponseDto;
import Likelion.Recruiting.domain.comment.dto.CommentDto;
import Likelion.Recruiting.domain.comment.dto.CreateCommentRequestDto;
import Likelion.Recruiting.domain.comment.entity.Comment;
import Likelion.Recruiting.domain.comment.entity.CommentLike;
import Likelion.Recruiting.domain.comment.repository.CommentLikeRepository;
import Likelion.Recruiting.domain.comment.repository.CommentRepository;
import Likelion.Recruiting.domain.comment.service.CommentLikeService;
import Likelion.Recruiting.domain.comment.service.CommentService;
import Likelion.Recruiting.domain.post.dto.PostDetailDto;
import Likelion.Recruiting.domain.post.dto.PostSimpleDto;
import Likelion.Recruiting.domain.post.dto.PostUpdateDto;
import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.entity.PostLike;
import Likelion.Recruiting.domain.post.repository.PostLikeRepository;
import Likelion.Recruiting.domain.post.repository.PostRepository;
import Likelion.Recruiting.domain.post.service.PostLikeService;
import Likelion.Recruiting.domain.post.service.PostService;
import Likelion.Recruiting.domain.reply.entity.Reply;
import Likelion.Recruiting.domain.reply.entity.ReplyLike;
import Likelion.Recruiting.domain.reply.repository.ReplyLikeRepository;
import Likelion.Recruiting.domain.reply.repository.ReplyRepository;
import Likelion.Recruiting.domain.reply.service.ReplyLikeService;
import Likelion.Recruiting.domain.reply.service.ReplyService;
import Likelion.Recruiting.domain.team.entity.Team;
import Likelion.Recruiting.domain.team.service.TeamService;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.post.entity.enums.MainCategory;
import Likelion.Recruiting.domain.post.entity.enums.SubCategory;
import Likelion.Recruiting.domain.user.repository.UserRepository;
import Likelion.Recruiting.domain.user.service.UserService;


import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final PostService postService;

    private final CommentService commentService;
    private final ReplyService replyService;
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;
    private final ReplyLikeService replyLikeService;
    private final UserService userService;
    private final TeamService teamService;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final ReplyRepository replyRepository;

    private final UserRepository userRepository;

    private final PostLikeRepository postLikeRepository;

    private final CommentLikeRepository commentLikeRepository;

    private final ReplyLikeRepository replyLikeRepository;

    @GetMapping("/community/posts/{mainCategory}/{subCategory}")//카테고리에따른 게시글 가져오는 api
    public PageResponseDto<PostSimpleDto> getSimplePosts(
                                          @AuthenticationPrincipal CustomOauthUserImpl customOauthUser,
                                          @PageableDefault(page = 1,size=5, sort="createdTime" ,direction = Sort.Direction.DESC)Pageable pageable,
                                          @PathVariable("mainCategory") String mainCategory,
                                          @PathVariable("subCategory") String subCategory) {
        User user = customOauthUser.getUser();
        PageResponseDto<PostSimpleDto> result;
        if (MainCategory.PROJECT == MainCategory.valueOf(mainCategory)) {
            Team team = teamService.findTeam(user.getId());
            result = postService.findProjectByCategory(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory), team.getId(), user, pageable);
        }
        else result = postService.findPostByCategory(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory),user,pageable);
        return result;
    }





    //-------------------------------------------------------------------------------------------------------
    //카테고리에따른 게시글 저장 api
    @PostMapping("/community/posts/{mainCategory}/{subCategory}")
    public CreatePostResponse savePost(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreatePostRequest createPostRequest, @PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {


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

        return new CreatePostResponse(savedPost.getId(),"게시글 작성 성공");
    }

    @Data
    @NoArgsConstructor
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
    @NoArgsConstructor
    static class CreatePostResponse {
        private Long id;
        private String message;

        public CreatePostResponse(Long id, String message) {
            this.id = id;
            this.message = message;
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/community/post/{postId}")//게시글 상세보기
    public PostDetailDto getPostDetail(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("postId") Long postId) {
        PostDetailDto result = postService.postDetailInfo(postId,customOauthUser.getUser());
        return result;
    }
    //---------------------------------------------------------------
    //게시글 수정
    @PatchMapping("/community/post/{postId}")
    public CreateResponseMessage updatePost(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody PostUpdateDto postUpdateDto, @PathVariable("postId") Long postId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return postService.updatePost(postId,user.getId(),postUpdateDto);
    }

    @Data
    @NoArgsConstructor
    static class EditPostRequest {
        private String title;
        private String body;
        private String thumbnailImage;

        public EditPostRequest(String body) {
            this.body = body;
        }
    }

    @DeleteMapping("/community/post/{postId}")
    public CreateResponseMessage deletePost(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("postId") Long postId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return postService.deletePost(postId,user.getId());
    }

    //---------------------------------------------------------------

    @PostMapping("/community/post/{postId}/like") // 게시글좋아용(이미 있으면 삭제, 없으면 저장)
    public CreateResponseMessage likePost(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@PathVariable("postId") Long postId){
        Post post = postRepository.findById(postId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        PostLike postLike = postLikeRepository.findOneByUserAndPost(user,post);
        if (postLike == null){
            postLikeService.createPostLike(user,post);
        }
        else postLikeService.deletePostLike(user,post);

        return new CreateResponseMessage((long)200, "좋아요 성공");
    }

    @GetMapping("/community/posts/{mainCategory}/{subCategory}/search")//카테고리에따른 게시글 가져오는 api
    public PageResponseDto<PostSimpleDto> searchPostOrProject(
            @AuthenticationPrincipal CustomOauthUserImpl customOauthUser,
            @PageableDefault(page = 0,size=5, sort="createdTime" ,direction = Sort.Direction.DESC)Pageable pageable,
            @PathVariable("mainCategory") String mainCategory,
            @PathVariable("subCategory") String subCategory,
            @RequestParam("search") String search) {
        User user = customOauthUser.getUser();
        PageResponseDto<PostSimpleDto> result;
        System.out.println(search);
        if (MainCategory.PROJECT == MainCategory.valueOf(mainCategory)) {
            Team team = teamService.findTeam(user.getId());
            result = postService.searchProject(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory), team.getId(), user, search, pageable);
        }
        else result = postService.searchPost(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory),user, search,pageable);
        return result;
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
    public CreatePostResponse saveComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreateCommentRequestDto request, @PathVariable("postId") Long postId) {
        Comment createdComment = Comment.builder()
                .body(request.getBody())
                .build();
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        Post post = postRepository.findById(postId).get();//역시 예외처리는 예외코드로
        Comment savedComment = commentService.createComment(createdComment,post,user);
        return new CreatePostResponse(savedComment.getId(), "댓글 작성 성공");
    }
    @PatchMapping("/community/comment/{commentId}") //예외처리~~~
    public CreateResponseMessage updateComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@RequestBody CreateCommentRequestDto request, @PathVariable("commentId") Long commentId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return commentService.updateComment(commentId,user.getId(),request);
    }
    @DeleteMapping("/community/comment/{commentId}") //예외처리~~~
    public CreateResponseMessage deleteComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("commentId") Long commentId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return commentService.deleteComment(commentId,user.getId());
    }
    @PostMapping("/community/comment/{commentId}/like") //댓글좋아용(이미 있으면 삭제, 없으면 저장)
    public CreateResponseMessage likeComment(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("commentId") Long commentId){
        Comment comment = commentRepository.findById(commentId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);//   유저는 헤더파일에서 뽑아오기
        CommentLike commentLike = commentLikeRepository.findOneByUserAndComment(user,comment);
        if (commentLike == null){
            commentLikeService.createCommentLike(user,comment);
        }
        else commentLikeService.deleteCommentLike(user,comment);

        return new CreateResponseMessage((long)200, "좋아요 성공");
    }


    //------------------------------------대댓글------------------------
    @PostMapping("/community/comment/{commentId}")//대댓글 저장 api
    public CreatePostResponse saveReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreateCommentRequestDto request, @PathVariable("commentId") Long commentId) {
        Reply createdReply = Reply.builder()
                .body(request.getBody())
                .build();
        // user insert partition
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        Comment comment = commentRepository.findById(commentId).get();//역시 예외처리는 예외코드로
        Reply savedReply = replyService.createReply(createdReply,comment,user);
        return new CreatePostResponse(savedReply.getId(),"대댓글 작성 성공");
    }
    @PatchMapping("/community/reply/{replyId}") //예외처리~~~
    public CreateResponseMessage updateReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser,@RequestBody CreateCommentRequestDto request, @PathVariable("replyId") Long replyId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return replyService.updateReply(replyId,user.getId(),request);
    }
    @DeleteMapping("/community/reply/{replyId}") //예외처리~~~
    public CreateResponseMessage updateReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("replyId") Long replyId){
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email);
        return replyService.deleteReply(replyId,user.getId());
    }

    @PostMapping("/community/reply/{replyId}/like") // 게시글좋아용(이미 있으면 삭제, 없으면 저장)
    public CreateResponseMessage likeReply(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @PathVariable("replyId") Long replyId){
        Reply reply= replyRepository.findById(replyId).get();//역시 예외처리는 예외코드로
        String email = customOauthUser.getUser().getEmail();
        User user = userService.findUser(email); //   유저는 헤더파일에서 뽑아오기
        ReplyLike replyLike = replyLikeRepository.findOneByUserAndReply(user,reply);
        if (replyLike == null){
            replyLikeService.createReplyLike(user,reply);
        }
        else replyLikeService.deleteReplyLike(user,reply);
        return new CreateResponseMessage((long)200, "좋아요 성공");
    }

}


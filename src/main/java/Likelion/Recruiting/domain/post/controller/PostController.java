package Likelion.Recruiting.domain.post.controller;

import Likelion.Recruiting.common.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.common.dto.CreateResponseMessage;
import Likelion.Recruiting.common.dto.PageResponseDto;
import Likelion.Recruiting.domain.post.dto.*;
import Likelion.Recruiting.domain.post.entity.Post;
import Likelion.Recruiting.domain.post.entity.PostLike;
import Likelion.Recruiting.domain.post.repository.PostLikeRepository;
import Likelion.Recruiting.domain.post.repository.PostRepository;
import Likelion.Recruiting.domain.post.service.PostLikeService;
import Likelion.Recruiting.domain.post.service.PostService;
import Likelion.Recruiting.domain.team.entity.Team;
import Likelion.Recruiting.domain.team.service.TeamService;
import Likelion.Recruiting.domain.user.entity.User;
import Likelion.Recruiting.domain.post.entity.enums.MainCategory;
import Likelion.Recruiting.domain.post.entity.enums.SubCategory;
import Likelion.Recruiting.domain.user.service.UserService;


import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final UserService userService;
    private final TeamService teamService;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

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
    public CreatePostResponseDto savePost(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestBody CreatePostRequestDto createPostRequestDto, @PathVariable("mainCategory") String mainCategory, @PathVariable("subCategory") String subCategory) {


        Post createdPost = Post.builder()
                .title(createPostRequestDto.getTitle())
                .body(createPostRequestDto.getBody())
                .thumbnailImage(createPostRequestDto.getThumbnailImage())
                .mainCategory(MainCategory.valueOf(mainCategory))
                .subCategory(SubCategory.valueOf(subCategory))
                .build();
        // user insert partition
        String email = customOauthUser.getUser().getEmail();
        //test
        User user = userService.findUser(email);
        Post savedPost = postService.createPost(createdPost,user);

        return new CreatePostResponseDto(savedPost.getId(),"게시글 작성 성공");
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

    @GetMapping("/community/posts/{mainCategory}/{subCategory}/search")//검색 API
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
}

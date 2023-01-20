package Likelion.Recruiting.controller;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.dto.DataResponseDto;
import Likelion.Recruiting.model.dto.PostSimpleDto;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import Likelion.Recruiting.repository.CommentRepository;
import Likelion.Recruiting.repository.PostRepository;
import Likelion.Recruiting.repository.ReplyRepository;
import Likelion.Recruiting.repository.UserRepository;
import Likelion.Recruiting.service.CommentService;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.ReplyService;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class MypageController {

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

    //----------------------작성한 게시글------------------------------
    @GetMapping("/mypage/posts")//카테고리에따른 게시글 가져오는 api
    public DataResponseDto getSimplePosts(@RequestHeader("HEADER") String header) {
        Long userId = Long.valueOf(1);
        User user = userRepository.findById(userId).get(); // 옵셔널이므로 id없을시 예외처리할때 예외코드날아감 -->try catch쓰기
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostSimpleDto> result = posts.stream()
                .map(p -> new PostSimpleDto(p,user))
                .collect(toList());
        return new DataResponseDto(result.size(), result);
    }

    //
}

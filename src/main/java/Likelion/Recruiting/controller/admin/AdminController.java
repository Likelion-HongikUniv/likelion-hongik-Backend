package Likelion.Recruiting.controller.admin;

import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.UserForm;
import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.service.admin.CommentService;
import Likelion.Recruiting.service.admin.PostService;
import Likelion.Recruiting.service.admin.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public AdminController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;

    }

    @RequestMapping("/admin")
    private String main() {
        return "admin/main";
    }


    //--- User Controller ---//

    // 회원목록 조회
    @GetMapping("admin/users")
    public String userList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);

        return "admin/users";
    }

    // 회원 정보 수정 - get : 수정 form 렌더링
    @GetMapping("admin/users/{userId}/edit")
    public String userForm(@PathVariable("userId") Long userId, Model model) {
        User user = (User) userService.findOne(userId);

        User form = new User();
        form.setId(user.getId());
        form.setName(user.getName());
        form.setEmail(user.getEmail());
        form.setNickname(user.getNickname());
        form.setMajor(user.getMajor());
        form.setStudentId(user.getStudentId());
        form.setPart(user.getPart());
        form.setRole((user.getRole()));

        model.addAttribute("form", form);

        return "admin/updateUserForm";
    }

    // 회원 정보 수정 - post : 수정된 내용 저장
    @PostMapping("admin/users/{userId}/edit")
    public String updateUser(@PathVariable Long userId, @ModelAttribute("form") UserForm form) {

        userService.updateUser(userId
                ,form.getName()
                ,form.getEmail()
                ,form.getMajor()
                ,form.getNickname()
                ,form.getPart()
                ,form.getRole()
                ,form.getStudentId());

        return "redirect:/admin/users";
    }

    // 회원 삭제
    @GetMapping("admin/users/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);

        return "redirect:/admin/users";
    }

    // User가 작성한 글/댓글 조회
    @GetMapping("admin/users/{userId}/writing")
    public String userWriting(@PathVariable("userId") Long userId, Model model) {
        User user = (User) userService.findOne(userId);
        List<Post> posts = postService.findPostsByUserId(userId);
        List<Comment> comments = commentService.findCommentsByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("comments", comments);

        return "admin/userWriting";
    }

    // User가 작성한 글/댓글 조회 - 게시글 전체삭제
    @GetMapping("admin/users/{userId}/posts/delete")
    public String postDeleteAll(@PathVariable("userId") Long userId) {

        postService.deletePostByUser(userId);

        return "redirect:/admin/users/{userId}/writing";

    }

    // User가 작성한 글/댓글 조회 - 댓글 전체삭제
    @GetMapping("admin/users/{userId}/comments/delete")
    public String commentDeleteAll(@PathVariable("userId") Long userId) {

        commentService.deleteCommentByUser(userId);

        return "redirect:/admin/users/{userId}/writing";
    }

    // User가 작성한 글/댓글 조회 - 게시글 1개 삭제
    @GetMapping("admin/users/{userId}/posts/{postId}/delete")
    public String postDelete(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {

        postService.deletePostOne(postId);

        return "redirect:/admin/users/{userId}/writing";
    }

    // User가 작성한 글/댓글 조회 - 댓글 1개 삭제
    @GetMapping("admin/users/{userId}/comments/{commentId}/delete")
    public String commentDelete(@PathVariable("userId") Long userId, @PathVariable("commentId") Long commentId) {

        commentService.deleteCommentOne(commentId);

        return "redirect:/admin/users/{userId}/writing";
    }


}


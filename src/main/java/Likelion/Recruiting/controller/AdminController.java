package Likelion.Recruiting.controller;

import Likelion.Recruiting.model.Comment;
import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.UserForm;
import Likelion.Recruiting.service.CommentService;
import Likelion.Recruiting.service.PostService;
import Likelion.Recruiting.service.UserService;
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
    @GetMapping("admin/users")
    public String userList(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);

        return "admin/users";
    }

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

        model.addAttribute("form", form);

        return "admin/updateUserForm";
    }

    @PostMapping("admin/users/{userId}/edit")
    public String updateUser(@PathVariable Long userId, @ModelAttribute("form") UserForm form) {

        userService.updateUser(userId
                ,form.getName()
                ,form.getEmail()
                ,form.getMajor()
                ,form.getNickname()
                ,form.getPart()
                ,form.getStudentId());

        return "redirect:/admin/users";
    }

    @GetMapping("admin/users/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);

        return "redirect:/admin/users";
    }

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


    @GetMapping("admin/users/{userId}/posts/delete")
    public String postDeleteAll(@PathVariable("userId") Long userId) {

        postService.deletePostByUser(userId);

        return "redirect:/admin/users/{userId}/writing";

    }

    @GetMapping("admin/users/{userId}/comments/delete")
    public String commentDeleteAll(@PathVariable("userId") Long userId) {

        commentService.deleteCommentByUser(userId);

        return "redirect:/admin/users/{userId}/writing";
    }


}


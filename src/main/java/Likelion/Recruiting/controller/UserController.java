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
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public UserController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }


    @GetMapping("admin/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();

        model.addAttribute("users", users);

        return "admin/users";
    }

    @GetMapping("admin/users/{userId}/edit")
    public String updateUserForm(@PathVariable("userId") Long userId, Model model) {
        User user = (User) userService.findOne(userId);

        User form = new User();
        form.setId(user.getId());
        form.setName(user.getName());
        form.setEmail(user.getEmail());
        form.setNickname(user.getNickname());
        form.setMajor(user.getMajor());
        form.setStudentId(user.getStudentId());
        form.setPart(user.getPart());
        form.setTeam_id(user.getTeam_id());

        model.addAttribute("form", form);

        return "admin/updateUserForm";
    }

    @PostMapping("admin/users/{userId}/edit")
    public String updateUser(@PathVariable Long userId, @ModelAttribute("form") UserForm form) {

        User book = new User();

        userService.updateUser(userId
                ,form.getName()
                ,form.getEmail()
                ,form.getMajor()
                ,form.getNickname()
                ,form.getPart()
                ,form.getStudentId()
                ,form.getTeam_id()
        );

        return "redirect:/admin/users";
    }

    @GetMapping("admin/users/{userId}/delete")
    public String delete(@PathVariable("userId") Long userId) {
        User user = (User) userService.findOne(userId);

        userService.deleteUser(userId);

        return "redirect:/admin/users";
    }

    @GetMapping("admin/users/{userId}/writing")
    public String writing(@PathVariable("userId") Long userId, Model model) {
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

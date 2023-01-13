package Likelion.Recruiting.controller;

import Likelion.Recruiting.domain.Post;
import Likelion.Recruiting.domain.User;
import Likelion.Recruiting.domain.UserForm;
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

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("admin/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();

        model.addAttribute("users", users);

        System.out.println(users);

        return "admin/users";
    }

    @GetMapping("admin/users/{userId}/edit")
    public String updateUserForm(@PathVariable("userId") Long userId, Model model) {
        User user = (User) userService.findOne(userId);

        User form = new User();
        form.setId(user.getId());
        form.setName(user.getName());
        form.setEmail(user.getEmail());

        model.addAttribute("form", form);

        return "admin/updateUserForm";
    }

    @PostMapping("admin/users/{userId}/edit")
    public String updateUser(@PathVariable Long userId, @ModelAttribute("form") UserForm form) {

        User book = new User();

        userService.updateUser(userId, form.getName(), form.getEmail());

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
        List<User> users = userService.findUsers();
        List<Post> posts = postService.findPosts();

        model.addAttribute("users", users);
        model.addAttribute("posts", posts);

        // user_id를 가지고 post랑 join 해야됨
        // user.id = user_id and post.author = user.name 인 post를 가져와서
        // 해당 Html 파일에 넘겨줘야됨

        return "admin/userWriting";
    }


}

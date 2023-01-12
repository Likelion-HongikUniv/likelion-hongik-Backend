package Likelion.Recruiting.controller;

import Likelion.Recruiting.domain.User;
import Likelion.Recruiting.domain.UserForm;
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

    public UserController(UserService userService) {
        this.userService = userService;
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
        form.setName(user.getEmail());

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



}

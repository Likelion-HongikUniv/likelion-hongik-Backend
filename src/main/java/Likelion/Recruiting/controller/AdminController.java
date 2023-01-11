package Likelion.Recruiting.controller;

import Likelion.Recruiting.domain.User;
import Likelion.Recruiting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin")
    private String main() {
        return "admin/main";
    }

    @GetMapping("admin/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("admin/members")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);

        System.out.println(users);

        return "admin/members";
    }

    @GetMapping("admin/volunteers")
    public String volunteer() {
        return "admin/volunteers";
    }

    @GetMapping("/admin/tables")
    public String tables() {
        return "admin/tables";
    }
}


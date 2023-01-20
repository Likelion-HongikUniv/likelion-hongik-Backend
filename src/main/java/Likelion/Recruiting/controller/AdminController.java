package Likelion.Recruiting.controller;

import Likelion.Recruiting.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("admin/volunteers")
    public String volunteer() {
        return "admin/volunteers";
    }

}


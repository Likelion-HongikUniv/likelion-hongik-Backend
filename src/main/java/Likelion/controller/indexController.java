package Likelion.controller;

import Likelion.config.auth.dto.SessionUser;
import Likelion.model.User;
import Likelion.model.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class indexController {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @GetMapping("/")
    String index(Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        if(sessionUser != null){
            model.addAttribute("message", sessionUser.getName());
            Optional<User> user = userRepository.findByEmail(sessionUser.getEmail());

        }
        else{
            model.addAttribute("message", "hello~");
        }

        return "index2";
    }



    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){

        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @PostMapping("/accounts/detail_info/")
    public @ResponseBody String detail(){

        return "detail";
    }


}

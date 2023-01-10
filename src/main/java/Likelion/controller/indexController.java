package com.recruit.recruit11.controller;

import com.recruit.recruit11.config.auth.PrincipalDetails;
import com.recruit.recruit11.config.auth.dto.SessionUser;
import com.recruit.recruit11.model.User;
import com.recruit.recruit11.model.UserRepository;
import com.recruit.recruit11.model.dto.ProfileDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails = " + principalDetails.getUser().getUsername());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @PostMapping("/accounts/detail_info/")
    String detail(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody ProfileDto profileDto){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());


//        System.out.println("req = " + req);
//        System.out.println("requestbody = " + requestbody);
//        System.out.println("user = " + user);
        return "detail";
    }


}

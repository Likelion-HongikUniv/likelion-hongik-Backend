package Likelion.Recruiting.controller;

import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final UserService userService;

    // 여기서 JWT를 돌려줄 것이다.
    @PostMapping("/v1/token")
    public NavbarDto testOAuthLogin(@RequestBody Long uid) throws IOException {
        return userService.takeJwt(uid);
    }
}

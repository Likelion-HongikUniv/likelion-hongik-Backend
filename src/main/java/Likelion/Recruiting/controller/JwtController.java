package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.RefreshException;
import Likelion.Recruiting.model.dto.NavbarDto;
import Likelion.Recruiting.model.dto.JWTDto;
import Likelion.Recruiting.service.TokenService;
import Likelion.Recruiting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final UserService userService;
    private final TokenService tokenService;

    // 여기서 JWT를 돌려줄 것이다.
    @PostMapping("/v1/token")
    public NavbarDto testOAuthLogin(@RequestBody Long uid) throws IOException {
        return tokenService.takeJwtAndRt(uid);
    }

    @GetMapping("/refresh")
    public JWTDto takeRefreshToken(@AuthenticationPrincipal CustomOauthUserImpl customOauthUser, @RequestHeader(value = "refresh_token") String header){

        try {
            // user 정보 추출
            Long uid = customOauthUser.getUser().getId();
            return tokenService.makeJwt(uid);
        }
        catch (NullPointerException ex){
            tokenService.deleteRt(header);
            throw new RefreshException(ErrorCode.EXPIRED_RT.getErrorCode(), "refresh_token 만료");
        }
    }

}

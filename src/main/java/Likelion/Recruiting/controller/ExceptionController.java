package Likelion.Recruiting.controller;

import Likelion.Recruiting.config.auth.CustomOauthUserImpl;
import Likelion.Recruiting.exception.DuplicationException;
import Likelion.Recruiting.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class ExceptionController {

    @GetMapping("/accounts/check")
    public ResponseEntity notFound() {
        // 밑 코드는 service 계층으로 가야 함
        throw new DuplicationException(ErrorCode.DUPLICATE_NICKNAME.getErrorCode(), ErrorCode.DUPLICATE_NICKNAME.getErrorMessage());
    }
}

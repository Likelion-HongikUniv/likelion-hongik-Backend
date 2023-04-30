package Likelion.Recruiting.domain.user.controller;

import Likelion.Recruiting.common.exception.ErrorCode;
import Likelion.Recruiting.common.exception.customException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExceptionController {

    @GetMapping("/exception/entrypoint")
    public void entryPoint() {
        throw new customException(ErrorCode.NO_LOGIN);
    }

    @GetMapping("/exception/access")
    public void denied() {
        throw new customException(ErrorCode.NO_ADMIN);
    }
}

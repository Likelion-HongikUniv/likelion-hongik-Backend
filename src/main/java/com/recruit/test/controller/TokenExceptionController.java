package com.recruit.test.controller;

import com.recruit.test.exception.ErrorCode;
import com.recruit.test.exception.customException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

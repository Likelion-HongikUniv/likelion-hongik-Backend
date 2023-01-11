package com.recruit.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class customException extends RuntimeException {
    private ErrorCode errorCode;
}

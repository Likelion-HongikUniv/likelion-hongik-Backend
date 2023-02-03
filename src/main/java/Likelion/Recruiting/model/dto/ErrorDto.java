package Likelion.Recruiting.model.dto;

import Likelion.Recruiting.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorDto {

    private int code;
    private String message;

    public ErrorDto(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

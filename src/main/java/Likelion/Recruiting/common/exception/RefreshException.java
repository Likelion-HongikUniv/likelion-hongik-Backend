package Likelion.Recruiting.common.exception;

import lombok.Getter;

@Getter
public class RefreshException extends RuntimeException{

    private int code;
    private String message;

    public RefreshException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

package Likelion.Recruiting.common.exception;

import lombok.Getter;

@Getter
public class UserException extends NullPointerException{

    private int code;
    private String message;

    public UserException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public UserException(String s, int code, String message) {
        super(s);
        this.code = code;
        this.message = message;
    }
}

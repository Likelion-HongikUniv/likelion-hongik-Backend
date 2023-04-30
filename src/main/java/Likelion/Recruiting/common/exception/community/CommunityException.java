package Likelion.Recruiting.common.exception.community;

import Likelion.Recruiting.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CommunityException extends RuntimeException {

    private int code;
    private String message;
    public CommunityException(ErrorCode errorCode){
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getErrorMessage();
    }
}

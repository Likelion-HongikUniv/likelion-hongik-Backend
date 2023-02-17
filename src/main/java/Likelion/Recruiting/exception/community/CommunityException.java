package Likelion.Recruiting.exception.community;

import Likelion.Recruiting.exception.ErrorCode;

public class CommunityException extends RuntimeException {

    private int code;
    private String message;
    public CommunityException(ErrorCode errorCode){
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getErrorMessage();
    }
}

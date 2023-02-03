package Likelion.Recruiting.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ADMIN_TOKEN(1001, "관리자 암호가 일치하지않습니다"),
    SAME_EMAIL(1002, "동일한 이메일이 존재합니다."),
    NO_USER(1003, "없는 사용자입니다."),
    NO_LOGIN(1004, "로그인이 필요합니다"),
    NO_ADMIN(1005, "권한이 없는 사용자입니다"),
    DUPLICATE_NICKNAME(1006, "닉네임이 중복됩니다.");
//    BOARD_NOT_FOUND(1007, "게시글을 찾을 수 없습니다.");

    private int errorCode;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

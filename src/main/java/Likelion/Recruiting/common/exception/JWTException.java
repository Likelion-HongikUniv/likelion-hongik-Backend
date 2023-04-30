package Likelion.Recruiting.common.exception;


public class JWTException extends io.jsonwebtoken.JwtException {

    private final int code;

    public JWTException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

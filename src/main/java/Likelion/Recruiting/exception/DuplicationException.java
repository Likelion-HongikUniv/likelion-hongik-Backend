package Likelion.Recruiting.exception;

public class DuplicationException extends RuntimeException {
    public final static int INDEX_NOT_FOUND = 1006;

    private final int code;
    public int getCode() {
        return code;
    }
    public DuplicationException(int code) {
        this.code = code;
    }
    public DuplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

}

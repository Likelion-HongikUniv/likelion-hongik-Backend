package Likelion.Recruiting.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Builder
@NoArgsConstructor
public class ErrorResponseDto {

    private int code;
    private HttpStatus httpStatus;
    private String message;

    @Builder
    public ErrorResponseDto(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

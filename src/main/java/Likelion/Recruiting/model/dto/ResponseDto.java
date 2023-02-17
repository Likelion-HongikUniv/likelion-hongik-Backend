package Likelion.Recruiting.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

@Data
@Getter
@Builder
@NoArgsConstructor
public class ResponseDto {

    private int code;
    private HttpStatus httpStatus;
    private String message;

    @Builder
    public ResponseDto(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

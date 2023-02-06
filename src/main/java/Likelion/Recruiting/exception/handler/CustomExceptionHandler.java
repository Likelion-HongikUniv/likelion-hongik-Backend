package Likelion.Recruiting.exception.handler;

import Likelion.Recruiting.exception.DuplicationException;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.JWTException;
import Likelion.Recruiting.model.dto.ErrorDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorDto> handleDuplicateNickname(DuplicationException ex){
        ErrorDto errorDto = new ErrorDto(ex.getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }


//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ErrorDto> expiredJwt(ExpiredJwtException ex){
//        ErrorDto errorDto = new ErrorDto(ErrorCode.VALIDATE_JWT.getErrorCode(), ex.getMessage());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
//    }

}

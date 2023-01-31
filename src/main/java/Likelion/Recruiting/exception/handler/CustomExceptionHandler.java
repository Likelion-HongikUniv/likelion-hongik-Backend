package Likelion.Recruiting.exception.handler;

import Likelion.Recruiting.exception.DuplicationException;
import Likelion.Recruiting.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorDto> handleDuplicateNickname(DuplicationException ex){
        System.out.println("ex = " + ex);
        ErrorDto errorDto = new ErrorDto(ex.getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }
}

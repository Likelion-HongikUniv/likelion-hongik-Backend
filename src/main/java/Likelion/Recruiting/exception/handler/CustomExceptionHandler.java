package Likelion.Recruiting.exception.handler;

import Likelion.Recruiting.exception.DuplicationException;
import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.JWTException;
import Likelion.Recruiting.exception.UserException;
import Likelion.Recruiting.model.dto.ErrorDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriComponentsBuilder;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorDto> handleDuplicateNickname(DuplicationException ex){
        ErrorDto errorDto = new ErrorDto(ex.getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDto> expiredJwt(UserException ex){
        System.out.println("excep handler");
        ErrorDto errorDto = new ErrorDto(ex.getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

//    @ExceptionHandler(UserException.class)
//    public void login(UserException ex){
//        String tartgetUri;
//        tartgetUri = UriComponentsBuilder
////                .fromUriString("https://likelionhongik.com/")
//                .fromUriString("http://localhost:3000/")
//                .path("/ing")
//                .queryParam("UID", -1)
//                .build().toUriString();
//        getRedirectStrategy().sendRedirect(request, response, tartgetUri);
//    }

}

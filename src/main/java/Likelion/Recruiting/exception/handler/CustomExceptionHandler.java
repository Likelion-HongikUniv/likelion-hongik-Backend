package Likelion.Recruiting.exception.handler;

import Likelion.Recruiting.exception.*;
import Likelion.Recruiting.exception.community.CommunityErrorDto;
import Likelion.Recruiting.exception.community.CommunityException;
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

    @ExceptionHandler(RefreshException.class)
    public ResponseEntity<ErrorDto> expiredRT(RefreshException ex){
        ErrorDto errorDto = new ErrorDto(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(CommunityException.class)
    public ResponseEntity<CommunityErrorDto> communityError(CommunityException ex){
        System.out.println("community exception");
        CommunityErrorDto communityErrorDto = new CommunityErrorDto(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(communityErrorDto);
    }

}

package Likelion.Recruiting.common.exception.handler;

import Likelion.Recruiting.common.exception.DuplicationException;
import Likelion.Recruiting.common.exception.RefreshException;
import Likelion.Recruiting.common.exception.UserException;
import Likelion.Recruiting.common.exception.community.CommunityErrorDto;
import Likelion.Recruiting.common.exception.community.CommunityException;
import Likelion.Recruiting.common.exception.customException;
import Likelion.Recruiting.common.dto.ErrorDto;
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

    @ExceptionHandler(customException.class)
    public ResponseEntity<ErrorDto> error(customException ex){
        ErrorDto errorDto = new ErrorDto(ex.getErrorCode().getErrorCode(), ex.getErrorCode().getErrorMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDto);
    }
    @ExceptionHandler(CommunityException.class)
    public ResponseEntity<CommunityErrorDto> communityError(CommunityException ex){
        System.out.println("community exception");
        CommunityErrorDto communityErrorDto = new CommunityErrorDto(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(communityErrorDto);
    }

}

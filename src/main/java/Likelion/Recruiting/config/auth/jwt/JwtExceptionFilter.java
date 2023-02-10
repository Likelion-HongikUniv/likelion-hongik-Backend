package Likelion.Recruiting.config.auth.jwt;

import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.JWTException;
import Likelion.Recruiting.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); // go to 'JwtAuthenticationFilter'
        } catch (JWTException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex, ex.getCode());
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex, int code) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ErrorDto errorDto = new ErrorDto(code, ex.getMessage());
//        JwtExceptionResponse jwtExceptionResponse = new JwtExceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//        res.getWriter().write(errorDto.convertToJson(errorDto.getCode(), errorDto.getMessage()).toString());
        res.getWriter().write(errorDto.convertObjectToJsonString(errorDto));
    }
}

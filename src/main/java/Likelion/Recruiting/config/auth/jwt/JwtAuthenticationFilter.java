package Likelion.Recruiting.config.auth.jwt;

import Likelion.Recruiting.exception.ErrorCode;
import Likelion.Recruiting.exception.customException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean { // 사용자가 로그인을 했을 때 Request에 가지고 있는 Token을 해석해주는 역할

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // header에서 jwt를 받아온다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 토큰이 유효한지 검사한다.
        if ( token != null && jwtTokenProvider.validateToken(token)){
            // 토큰을 인증한다 -> 실제 존재하는 유저의 토큰인지
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // securityContext에 Authentication 객체를 저장한다.
            // token이 인증된 상태를 유지하도록 context를 유지해줌 -> ???
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}

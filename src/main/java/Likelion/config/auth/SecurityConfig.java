package com.recruit.recruit11.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws
            Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 1차 위처럼 명시된 역할을 가지고 있지 않으면 각 페이지로 접근할 수 없다 -> 403에러
                .and()
                .formLogin()
                .loginPage("/loginForm") // 2차 여기서는 로그인이 되어있지 않으면 user, manager, admin 페이지에 접근할 시 login 페이지로 연결된다.
//                .failureHandler(authenticationFailureHandler)
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다 -> 내가 controller에 /login을 만들지 않아도 된다.
                .defaultSuccessUrl("/detailinfo")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        return http.build();
    }


}

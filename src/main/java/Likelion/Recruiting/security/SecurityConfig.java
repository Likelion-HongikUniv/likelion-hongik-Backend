package Likelion.Recruiting.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin/") // 해당 경로는 접근 막겠다. 이 코드 없으면 전부 다막힘
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin();

        return http.build();

    }
}

package Likelion.Recruiting.common.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig2 {

    @Order(0)
    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/admin/**")
                .authorizeRequests()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin();


        return http.build();

    }

}

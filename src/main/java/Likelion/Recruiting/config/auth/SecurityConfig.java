package Likelion.Recruiting.config.auth;

import Likelion.Recruiting.config.auth.jwt.JwtAuthenticationFilter;
import Likelion.Recruiting.config.auth.jwt.JwtExceptionFilter;
import Likelion.Recruiting.config.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(encoder.encode("admin1234")).roles("ADMIN").build());

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //@Order(SecurityProperties.BASIC_AUTH_ORDER)
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and();
//                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 스프링시큐리티가 세션쿠키방식으로 작동하지 않게 하는 것임.

         http
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .anyRequest().permitAll() // 1차 위처럼 명시된 역할을 가지고 있지 않으면 각 페이지로 접근할 수 없다 -> 403에러
                .and()

                 .formLogin()
                 .and()

//                 .logout()
//                 .logoutUrl("/out")
////                 .deleteCookies("JSESSIONID", "refreshToken")
////                 .addLogoutHandler()
//                 .permitAll()
//                 .and()

                .oauth2Login()
                .successHandler(customAuthSuccessHandler) // 꼭 추가해주세요!
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        //JwtFilter 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addExposedHeader("Content-Disposition");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("X-AUTH-TOKEN")
//                .allowCredentials(true)
                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("GET", "POST")
                .allowedOriginPatterns("*");
//        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
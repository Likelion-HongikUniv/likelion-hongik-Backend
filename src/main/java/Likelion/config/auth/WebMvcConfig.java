package com.recruit.recruit11.config.auth;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MustacheViewResolver resolver = new MustacheViewResolver();

        resolver.setCharset("UTF-8"); // 내가 만든 view의 기본적인 인코딩은 utf-8
        resolver.setContentType("text/html;charset=UTF-8"); // 내가 던지는 파일은 기본적으로 html 파일이다.
        resolver.setPrefix("classpath:/templates/"); // 경로는 이렇게 이다.
        resolver.setSuffix(".html");

        registry.viewResolver(resolver); // registry로 뷰 리졸버를 설정하는 코드
    }
}

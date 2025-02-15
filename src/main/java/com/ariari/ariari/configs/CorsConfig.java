package com.ariari.ariari.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "https://main.d2ux83pqp6klo1.amplifyapp.com/",
                                "https://release.d2ux83pqp6klo1.amplifyapp.com", "https://www.ariari.kr",
                                "https://ariari.kr", "http://localhost:3000", "http://localhost:3001")  // 3000번 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);  // 모든 헤더 허용
            }
        };
    }
}

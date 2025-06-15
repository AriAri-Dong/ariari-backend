package com.ariari.ariari.configs;

import com.ariari.ariari.commons.auth.springsecurity.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] PERMIT_ALL_PATTERNS = {
            // api 경로
            "/clubs/**",
            "/recruitments/**",
            "/schools/**",
            "/club-events/**",
            "/service-notices/**",
            "/applies/**",
            "/service-faq/**",
            "/service-faqs/**",
            "/report/**",
            "/apply-temps/**",
            "/club-notices/**",
            "/system-term/**",
            "/reports/**",
            "/members/**",
            "/member/**",
            "/attendances/**",
            "/club-members/**",
            "/club_members/**",
            "/unregister/**",
            "/sign-up/**",
            "/reissue/**",
            "/auth/**",
            "/login/**",
            "/club-faqs/**",
            "/club-activity/**",
            "/test/**",
            "/pass-review/**",
            "/club-review/**",
            "/club/**",


            // 에러 페이지
            "/error",

            // Swagger UI 관련 경로
            "/api-docs",
            "/api-docs/**",
            "/v3/api-docs",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/webjars/**",
            "/swagger-resources/**",
            "configuration/ui",
            "configuration/security",
            "/swagger-resources",
            "/v3/api-docs/**",
            "/manifest.json",


            // 운영에선 안슴
            // "/s3/**"
            // "/mail/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // session 사용 X
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PERMIT_ALL_PATTERNS).permitAll()
                .anyRequest().authenticated());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

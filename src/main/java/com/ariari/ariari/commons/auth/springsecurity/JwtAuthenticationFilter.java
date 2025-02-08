package com.ariari.ariari.commons.auth.springsecurity;

import com.ariari.ariari.commons.exception.CustomException;
import com.ariari.ariari.commons.exception.dto.ExceptionRes;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtManager.extractToken(request);

        if (token != null) {
            JwtAuthentication jwtAuthentication = new JwtAuthentication(token);

            try {
                Authentication authenticate = authenticationManager.authenticate(jwtAuthentication);
                SecurityContextHolder.getContext().setAuthentication(authenticate);
            } catch (CustomException e) {
                setErrorResponse(response, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, String message) throws IOException {
        ExceptionRes responseBody = ExceptionRes.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(message)
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

}

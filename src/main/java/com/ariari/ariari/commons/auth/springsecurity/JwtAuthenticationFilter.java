package com.ariari.ariari.commons.auth.springsecurity;

import com.ariari.ariari.commons.auth.springsecurity.domain.CustomUserDetails;
import com.ariari.ariari.commons.auth.springsecurity.domain.JwtAuthentication;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("========== JwtFilter start ==========");

        String token = jwtManager.extractToken(request);
//        log.info("token : {}", token);

        if (token == null) {
            JwtAuthentication jwtAuthentication = new JwtAuthentication();
        } else {
            JwtAuthentication jwtAuthentication = new JwtAuthentication(token);
            Authentication authenticate = authenticationManager.authenticate(jwtAuthentication);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            log.info("authentication : {}", authentication);
//            log.info("userDetails : {}", authentication.getDetails());
//            log.info("email : {}", ((CustomUserDetails) authentication.getDetails()).getMemberId());
//            log.info("authorities : {}", authentication.getAuthorities());
        }

        log.info("========== JwtFilter end : {} ==========", token);
        filterChain.doFilter(request, response);
    }

}

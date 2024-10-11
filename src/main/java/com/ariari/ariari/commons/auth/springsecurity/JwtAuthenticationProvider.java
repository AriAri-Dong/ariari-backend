package com.ariari.ariari.commons.auth.springsecurity;

import com.ariari.ariari.commons.auth.springsecurity.domain.CustomUserDetails;
import com.ariari.ariari.commons.auth.springsecurity.domain.JwtAuthentication;
import com.ariari.ariari.commons.manager.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtManager jwtManager;
    private final CustomUserDetailsService customUserDetailsService;
//    private final BannedTokenRepository bannedTokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String jwtToken = ((JwtAuthentication) authentication).getCredentials();

//        if (bannedTokenRepository.findByToken(jwtToken).isPresent()) {
//            throw new BannedJwtTokenException();
//        }

        jwtManager.validateToken(jwtToken);

        Long memberId = jwtManager.getMemberId(jwtToken);
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId.toString());

        return new JwtAuthentication(jwtToken, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

}
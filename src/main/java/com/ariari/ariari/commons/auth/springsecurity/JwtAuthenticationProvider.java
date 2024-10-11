package com.ariari.ariari.commons.auth.springsecurity;

import com.ariari.ariari.commons.exception.exceptions.BannedTokenException;
import com.ariari.ariari.commons.manager.JwtControlManager;
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
    private final JwtControlManager jwtControlManager;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String jwtToken = ((JwtAuthentication) authentication).getCredentials();

        if (jwtControlManager.isBannedToken(jwtToken)) {
            throw new BannedTokenException();
        }

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
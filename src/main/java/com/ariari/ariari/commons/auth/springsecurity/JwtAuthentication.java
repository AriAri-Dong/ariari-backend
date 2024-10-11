package com.ariari.ariari.commons.auth.springsecurity;

import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private String jwtToken;
    @Setter
    private CustomUserDetails userDetails;

    private boolean isAuthenticated;

    public JwtAuthentication() {
    }

    public JwtAuthentication(String jwtToken) {
        this.jwtToken = jwtToken;
        this.isAuthenticated = false;
    }

    public JwtAuthentication(String jwtToken, CustomUserDetails userDetails) {
        this.jwtToken = jwtToken;
        this.userDetails = userDetails;
        this.isAuthenticated = true;
    }

    @Override
    public String getCredentials() {
        return jwtToken;
    }

    @Override
    public CustomUserDetails getDetails() {
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getDetails().getAuthorities();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = true;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    /**
     * not used
     */
    @Override
    public String getName() {
        return null;
    }

}

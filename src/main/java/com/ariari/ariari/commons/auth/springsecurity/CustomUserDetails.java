package com.ariari.ariari.commons.auth.springsecurity;

import com.ariari.ariari.commons.exception.exceptions.NotAuthenticatedException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long memberId;

    @Setter
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public CustomUserDetails(Long memberId, Set<GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return memberId.toString();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * not used
     */
    @Override
    public String getPassword() {
        return null;
    }

    public static Long getMemberId(CustomUserDetails userDetails, boolean required) {
        if (userDetails != null) {
            return userDetails.getMemberId();
        } else {
            if (required) {
                throw new NotAuthenticatedException();
            } else {
                return null;
            }
        }
    }

}
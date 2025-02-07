package com.ariari.ariari.test.dto;

import com.ariari.ariari.commons.manager.JwtManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class TokenInfoRes {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;
    private List<? extends GrantedAuthority> authorities;
    private JwtManager.TokenType tokenType;
    private LocalDateTime expiredDateTime;

    public static TokenInfoRes createRes(Long memberId, List<? extends GrantedAuthority> authorities, JwtManager.TokenType tokenType, LocalDateTime expiredDateTime) {
        return new TokenInfoRes(
                memberId,
                authorities.stream().toList(),
                tokenType,
                expiredDateTime
        );
    }

}

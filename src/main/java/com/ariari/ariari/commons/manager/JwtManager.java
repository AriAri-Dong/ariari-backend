package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.exception.exceptions.ExpiredTokenException;
import com.ariari.ariari.commons.exception.exceptions.NotValidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtManager {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.access-expiration-time}")
    private Long ACCESS_EXPIRATION_TIME;

    @Value("${jwt.refresh-expiration-time}")
    private Long REFRESH_EXPIRATION_TIME;

    public String generateToken(Set<GrantedAuthority> authorities, Long memberId, TokenType tokenType) {
        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // 각 GrantedAuthority에서 authority 추출
                .collect(Collectors.toList());  // Set<String>으로 변환

        return Jwts.builder()
                .signWith(getSecretKey())
                .subject(memberId.toString())
                .claim("tokenType", tokenType.toString())
                .claim("authorities", authorityStrings)
                .issuedAt(new Date())
                .expiration(getNewExpirationDate(tokenType))
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            log.error("★★★ fail to validate token ★★★", e);
            throw new NotValidTokenException();
        }
    }

    public void validateRefresh(String refresh) {
        Claims payload = null;
        try {
            payload = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(refresh)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            log.error("fail to validate refresh", e);
            throw new NotValidTokenException();
        }

        if (payload == null) {
            log.info("payload is null");
            return;
        }

        String tokenType = (String) payload.get("tokenType");
        if (!tokenType.equals(TokenType.REFRESH_TOKEN.toString())) {
            throw new NotValidTokenException();
        }
    }

    public String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public Long getMemberId(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(payload.getSubject());
    }

    public Date getExpiration(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return payload.getExpiration();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        List<String> authorities = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("authorities", List.class);

        return AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));
    }

    public TokenType getTokenType(String token) {
        String tokenType = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("tokenType", String.class);

        return tokenType.equals("ACCESS_TOKEN") ? TokenType.ACCESS_TOKEN : TokenType.REFRESH_TOKEN;
    }

    public LocalDateTime getExpirationDate(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Date date = payload.getExpiration();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private Date getNewExpirationDate(TokenType tokenType) {
        long expirationTime = tokenType.equals(TokenType.ACCESS_TOKEN) ? ACCESS_EXPIRATION_TIME : REFRESH_EXPIRATION_TIME;
        return new Date(new Date().getTime() + expirationTime);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));
    }

    public enum TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

}

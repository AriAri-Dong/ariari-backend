package com.ariari.ariari.commons.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenRes {

    private String accessToken;
    private String refreshToken;
    private Boolean isFirstLogin;

    public static JwtTokenRes createRes(String accessToken, String refreshToken, Boolean isFirstLogin) {
        return new JwtTokenRes(accessToken, refreshToken, isFirstLogin);
    }

}

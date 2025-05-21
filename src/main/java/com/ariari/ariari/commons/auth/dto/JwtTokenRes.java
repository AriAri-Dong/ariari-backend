package com.ariari.ariari.commons.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenRes {

    private String accessToken;
    private String refreshToken;
    private String oAuthSignUpKey;

    public static JwtTokenRes createRes(String accessToken, String refreshToken, String oAuthSignUpKey) {
        return new JwtTokenRes(accessToken, refreshToken, oAuthSignUpKey);
    }

}

package com.ariari.ariari.commons.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessTokenRes {

    private String accessToken;

    public static AccessTokenRes createRes(String accessToken) {
        return new AccessTokenRes(
                accessToken
        );
    }

}

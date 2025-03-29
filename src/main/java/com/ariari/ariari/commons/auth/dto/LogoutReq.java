package com.ariari.ariari.commons.auth.dto;

import lombok.Data;

@Data
public class LogoutReq {

    private String accessToken;
    private String refreshToken;

}

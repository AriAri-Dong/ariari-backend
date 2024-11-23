package com.ariari.ariari.commons.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenReq {

    private String accessToken;
    private String refreshToken;

}

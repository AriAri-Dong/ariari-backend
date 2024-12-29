package com.ariari.ariari.commons.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "dddd", example = "{\\\"id\\\": \\\"12345\\\", \\\"name\\\": \\\"Hong gil dong\\\"}")
public class JwtTokenReq {

    @Schema(description = "엑세스토큰", example = "bearer없이 ......")
    private String accessToken;
    private String refreshToken;

}

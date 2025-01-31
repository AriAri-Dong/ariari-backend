package com.ariari.ariari.domain.school.auth.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "학교 인증 코드 요청 형식")
public class SchoolAuthReq {

    @Schema(description = "학교 이메일", example = "1sunj@naver.com")
    private String email;

}

package com.ariari.ariari.domain.school.auth.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "학교 인증 코드 입력 형식")
public class SchoolAuthCodeReq {

    @Schema(description = "이메일로 받은 학교 인증 코드", example = "abcdef")
    private String schoolAuthCode;

}

package com.ariari.ariari.domain.school.auth.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "학교 인증 코드 요청 형식")
public class SchoolAuthReq {
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "이메일 형식에 맞지 않습니다."
    )
    @Schema(description = "학교 이메일", example = "1sunj@naver.com")
    private String email;

}

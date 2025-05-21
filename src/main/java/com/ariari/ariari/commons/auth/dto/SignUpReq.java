package com.ariari.ariari.commons.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpReq {
    @Schema(description = "학교 이메일", example = "1sunj@naver.com")
    private String email;

    @Schema(description = "이메일로 받은 학교 인증 코드", example = "abcdef")
    private String schoolAuthCode;

    @Schema(description = "닉네임", example = "다정한토끼248")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;
}

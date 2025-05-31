package com.ariari.ariari.commons.auth.dto;

import com.ariari.ariari.domain.member.enums.ProfileType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpReq {
    @Schema(description = "학교 이메일", example = "1sunj@naver.com")
    private String email;

    @Schema(description = "프로필타입, 선택 안했다면 null도 가능 종류는" +
            "ARIARI_COW,\n" +
            "    ARIARI_TIGER,\n" +
            "    ARIARI_RABBIT,\n" +
            "    ARIARI_DRAGON,\n" +
            "    ARIARI_SNAKE,\n" +
            "    ARIARI_HORSE,\n" +
            "    ARIARI_SHEEP,\n" +
            "    ARIARI_MONKEY,\n" +
            "    ARIARI_CHICKEN,\n" +
            "    ARIARI_DOG,\n" +
            "    ARIARI_PIG", example = "ARIARI_MOUSE")
    private ProfileType profileType;

    @Schema(description = "이메일로 받은 학교 인증 코드", example = "abcdef")
    private String schoolAuthCode;

    @Schema(description = "닉네임", example = "다정한토끼248")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;
}

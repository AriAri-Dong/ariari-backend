package com.ariari.ariari.domain.member.member.dto.req;

import com.ariari.ariari.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 닉네임 수정 형식")
public class NicknameModifyReq {

    @Schema(description = "회원 닉네임", example = "1sunJ")
    private String nickname;

    public void modifyNickname(Member member) {
        member.setNickName(nickname);
    }

}

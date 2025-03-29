package com.ariari.ariari.domain.member.member.dto.req;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.enums.ProfileType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "회원 프로필 수정 형식")
public class ProfileModifyReq {

    @Schema(description = "회원 프로필", example = "ARIARI_DRAGON")
    private ProfileType profileType;

    public void modifyNickname(Member member) {
        member.setProfileType(profileType);
    }

}

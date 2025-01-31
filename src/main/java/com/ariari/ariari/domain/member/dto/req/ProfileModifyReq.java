package com.ariari.ariari.domain.member.dto.req;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.enums.ProfileType;
import lombok.Data;

@Data
public class ProfileModifyReq {

    private ProfileType profileType;

    public void modifyNickname(Member member) {
        member.setProfileType(profileType);
    }

}

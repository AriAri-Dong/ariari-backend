package com.ariari.ariari.domain.member.dto.req;

import com.ariari.ariari.domain.member.Member;
import lombok.Data;

@Data
public class NicknameModifyReq {

    private String nickname;

    public void modifyNickname(Member member) {
        member.setNickName(nickname);
    }

}

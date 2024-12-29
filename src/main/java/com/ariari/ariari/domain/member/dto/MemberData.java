package com.ariari.ariari.domain.member.dto;

import com.ariari.ariari.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberData {

    private Long id;
    private String nickname;

    public static MemberData fromEntity(Member member) {
        return new MemberData(
                member.getId(),
                member.getNickName()
        );
    }

}

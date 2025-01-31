package com.ariari.ariari.domain.member.dto;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.enums.ProfileType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String nickname;
    private ProfileType profileType;

    public static MemberData fromEntity(Member member) {
        return new MemberData(
                member.getId(),
                member.getNickName(),
                member.getProfileType()
        );
    }

}

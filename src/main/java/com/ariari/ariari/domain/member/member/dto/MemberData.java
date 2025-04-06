package com.ariari.ariari.domain.member.member.dto;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.enums.ProfileType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "회원 id", example = "673012345142938986")
    private Long memberId;

    @Schema(description = "회원 닉네임", example = "귀여운대머리")
    private String nickname;

    @Schema(description = "회원 프로필 타입", example = "ARIARI_MOUSE")
    private ProfileType profileType;

    public static MemberData fromEntity(Member member) {
        return new MemberData(
                member.getId(),
                member.getNickName(),
                member.getProfileType()
        );
    }

    public static List<MemberData> fromEntities(List<Member> members) {
        return members.stream().map(MemberData::fromEntity).toList();
    }

}

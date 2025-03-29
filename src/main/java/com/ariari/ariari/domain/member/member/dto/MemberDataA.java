package com.ariari.ariari.domain.member.member.dto;

import com.ariari.ariari.domain.member.Member;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberDataA {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "회원 id", example = "673012345142938986")
    private Long memberId;

    @Schema(description = "회원 닉네임", example = "귀여운대머리")
    private String nickname;

    public static MemberDataA fromEntity(Member member) {
        return new MemberDataA(
                member.getId(),
                member.getNickName()
        );
    }

    public static List<MemberDataA> fromEntities(List<Member> members) {
        return members.stream().map(MemberDataA::fromEntity).toList();
    }

}

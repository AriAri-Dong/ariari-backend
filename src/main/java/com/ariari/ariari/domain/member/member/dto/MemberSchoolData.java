package com.ariari.ariari.domain.member.member.dto;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.enums.ProfileType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberSchoolData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "회원 id", example = "673012345142938986")
    private Long memberId;

    @Schema(description = "학교 이름", example = "네이버대학교")
    private String schoolName;

    @Schema(description = "회원 닉네임", example = "귀여운대머리")
    private String nickname;

    @Schema(description = "회원 프로필 타입", example = "ARIARI_MOUSE")
    private ProfileType profileType;

    public static MemberSchoolData fromEntity(Member member) {
        return new MemberSchoolData(
                member.getId(),
                member.getSchool() != null ? member.getSchool().getName() : null,
                member.getNickName(),
                member.getProfileType()
        );
    }

    public static List<MemberSchoolData> fromEntities(List<Member> members) {
        return members.stream().map(MemberSchoolData::fromEntity).toList();
    }
}
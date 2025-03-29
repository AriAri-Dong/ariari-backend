package com.ariari.ariari.domain.club.clubmember.dto;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.member.member.dto.MemberData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Schema(description = "동아리 회원 데이터")
public class ClubMemberData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 회원 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 회원 활동명", example = "아리아리 원순재")
    private String name;
    @Schema(description = "동아리 회원 권한", example = "GENERAL")
    private ClubMemberRoleType clubMemberRoleType;
    @Schema(description = "동아리 회원 상태", example = "ACTIVE")
    private ClubMemberStatusType clubMemberStatusType;

    private MemberData memberData;

    public static ClubMemberData fromEntity(ClubMember clubMember) {
        if (clubMember == null) {
            return null;
        }

        return new ClubMemberData(
                clubMember.getId(),
                clubMember.getName(),
                clubMember.getClubMemberRoleType(),
                clubMember.getClubMemberStatusType(),
                MemberData.fromEntity(clubMember.getMember())
        );
    }

    public static List<ClubMemberData> fromEntities(List<ClubMember> clubMembers) {
        return clubMembers.stream().map(ClubMemberData::fromEntity).collect(Collectors.toList());
    }

}

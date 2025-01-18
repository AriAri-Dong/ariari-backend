package com.ariari.ariari.domain.club.clubmember.dto;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.member.dto.MemberData;
import com.ariari.ariari.domain.member.enums.ProfileType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ClubMemberData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private ClubMemberRoleType clubMemberRoleType;
    private ClubMemberStatusType clubMemberStatusType;
    private ProfileType profileType;

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
                clubMember.getMember().getProfileType(),
                MemberData.fromEntity(clubMember.getMember())
        );
    }

    public static List<ClubMemberData> fromEntities(List<ClubMember> clubMembers) {
        return clubMembers.stream().map(ClubMemberData::fromEntity).collect(Collectors.toList());
    }

}

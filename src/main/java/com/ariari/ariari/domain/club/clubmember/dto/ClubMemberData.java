package com.ariari.ariari.domain.club.clubmember.dto;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.member.enums.ProfileType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ClubMemberData {

    private Long id;
    private String name;
    private ClubMemberRoleType clubMemberRoleType;
    private ClubMemberStatusType clubMemberStatusType;
    private ProfileType profileType;

    public static ClubMemberData fromEntity(ClubMember clubMember) {
        if (clubMember == null) {
            return null;
        }

        return ClubMemberData.builder()
                .id(clubMember.getId())
                .name(clubMember.getName())
                .clubMemberRoleType(clubMember.getClubMemberRoleType())
                .clubMemberStatusType(clubMember.getClubMemberStatusType())
                .profileType(clubMember.getMember().getProfileType())
                .build();
    }

    public static List<ClubMemberData> fromEntities(List<ClubMember> clubMembers) {
        return clubMembers.stream().map(ClubMemberData::fromEntity).collect(Collectors.toList());
    }

}

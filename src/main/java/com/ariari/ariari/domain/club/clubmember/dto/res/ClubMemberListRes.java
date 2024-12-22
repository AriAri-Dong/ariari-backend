package com.ariari.ariari.domain.club.clubmember.dto.res;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubMemberListRes {

    private List<ClubMemberData> contents;

    public static ClubMemberListRes fromEntities(List<ClubMember> clubMembers) {
        return new ClubMemberListRes(ClubMemberData.fromEntities(clubMembers));
    }

}

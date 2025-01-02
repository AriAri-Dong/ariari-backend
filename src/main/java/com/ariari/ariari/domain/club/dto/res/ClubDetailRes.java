package com.ariari.ariari.domain.club.dto.res;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.dto.ClubData;
import com.ariari.ariari.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClubDetailRes {

    private ClubData clubData;
    private ClubMemberData clubMemberData;

    public static ClubDetailRes fromEntity(Club club, ClubMember reqClubMember, Member reqMember) {
        return new ClubDetailRes(
                ClubData.fromEntity(club, reqMember),
                ClubMemberData.fromEntity(reqClubMember)
        );
    }

}

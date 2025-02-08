package com.ariari.ariari.domain.club.club.dto.res;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "동아리 상세 조회 응답 (clubMemberData 는 나의 동아리 회원 데이터입니다. 만약 해당 동아리에 속하지 않았다면 null 입니다.)")
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

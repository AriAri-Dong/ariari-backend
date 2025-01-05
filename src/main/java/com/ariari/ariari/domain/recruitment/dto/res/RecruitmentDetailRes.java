package com.ariari.ariari.domain.recruitment.dto.res;

import com.ariari.ariari.domain.club.dto.ClubData;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecruitmentDetailRes {

    private RecruitmentData recruitmentData;
    private ClubData clubData;
    private Integer bookmarks;

    private Boolean isMyClub;
    private Boolean isMyApply;

    public static RecruitmentDetailRes fromEntity(Recruitment recruitment, Integer bookmarks, Member reqMember, Boolean isMyClub, Boolean isMyApply) {
        return new RecruitmentDetailRes(
                RecruitmentData.fromEntity(recruitment, reqMember),
                ClubData.fromEntity(recruitment.getClub(), reqMember),
                bookmarks,
                isMyClub,
                isMyApply
        );
    }

}

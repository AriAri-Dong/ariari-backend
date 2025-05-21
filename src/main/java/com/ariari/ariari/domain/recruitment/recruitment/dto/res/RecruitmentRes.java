package com.ariari.ariari.domain.recruitment.recruitment.dto.res;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.recruitment.dto.RecruitmentData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecruitmentRes {

    private RecruitmentData recruitmentData;

    public static RecruitmentRes createRes(Recruitment recruitment, Member reqMember) {
        return new RecruitmentRes(
                recruitment == null ? null : RecruitmentData.fromEntity(recruitment, reqMember)
        );
    }

}

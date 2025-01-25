package com.ariari.ariari.domain.recruitment.dto.res;

import com.ariari.ariari.domain.club.dto.ClubData;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.applyform.dto.ApplyFormData;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import com.ariari.ariari.domain.recruitment.note.dto.RecruitmentNoteData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecruitmentDetailRes {

    private RecruitmentData recruitmentData;
    private List<RecruitmentNoteData> recruitmentNoteDataList;
    private ClubData clubData;
    private ApplyFormData applyFormData;
    private Integer bookmarks;

    private Boolean isMyClub;
    private Boolean isMyApply;

    public static RecruitmentDetailRes fromEntity(Recruitment recruitment, Integer bookmarks, Member reqMember, Boolean isMyClub, Boolean isMyApply) {
        return new RecruitmentDetailRes(
                RecruitmentData.fromEntity(recruitment, reqMember),
                RecruitmentNoteData.fromEntities(recruitment.getRecruitmentNotes()),
                ClubData.fromEntity(recruitment.getClub(), reqMember),
                ApplyFormData.fromEntity(recruitment.getApplyForm()),
                bookmarks,
                isMyClub,
                isMyApply
        );
    }

}

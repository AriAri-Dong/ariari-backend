package com.ariari.ariari.domain.recruitment.recruitment.dto.res;

import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.applyform.dto.ApplyFormData;
import com.ariari.ariari.domain.recruitment.recruitment.dto.RecruitmentData;
import com.ariari.ariari.domain.recruitment.note.dto.RecruitmentNoteData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "모집 상세 응답")
public class RecruitmentDetailRes {

    private RecruitmentData recruitmentData;
    @Schema(description = "모집 추가 항목 데이터 리스트")
    private List<RecruitmentNoteData> recruitmentNoteDataList;
    private ClubData clubData;
    private ApplyFormData applyFormData;
    @Schema(description = "모집을 북마크한 회원 수", example = "53")
    private Integer bookmarks;

    @Schema(description = "내 동아리의 모집인지 여부", example = "true")
    private Boolean isMyClub;
    @Schema(description = "내가 지원한 모집인지 여부", example = "false")
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

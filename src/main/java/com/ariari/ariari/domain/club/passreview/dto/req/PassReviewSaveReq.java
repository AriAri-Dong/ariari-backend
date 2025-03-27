package com.ariari.ariari.domain.club.passreview.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.club.passreview.note.dto.req.PassReviewNoteReq;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.recruitment.enums.ProcedureType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PassReviewSaveReq {
    @Schema(description = "합격후기 제목", example = "제목")
    private String title;
    @Schema(description = "동아리 모집 전형", example = "DOCUMENT : 서류 , INTERVIEW : 서류 + 면접")
    private ProcedureType procedureType;
    @Schema(description = "면접 방식", example = "ONLINE : 온라인, OFFLINE : 대면, CALL : 전화")
    private InterviewType interviewType;
    @Schema(description = "면접 방식(면접관 비율)", example = "ONE_VS_ONE : 지원자1/면접관1, ONE_VS_MANY : 지원자1/면접관 다수, MANY_VS_MANY : 그룹면접")
    private InterviewRatioType interviewRatioType;
    @Schema(description = "면접 분위기", example = "편안한 1 ~ 엄숙한 5")
    private Integer interviewMood;
    @Schema(description = "면접문항 및 서류문항들", example = "문항,답변,타입으로 구성된 면접 문항 및 서류 문항들")
    private List<PassReviewNoteReq> passReviewNotes;

    public PassReview toEntity(PassReviewSaveReq passReviewSaveReq, Club club, Member member){
        List<PassReviewNote> passReviewNotes = passReviewSaveReq.getPassReviewNotes().stream().map(PassReviewNoteReq::toEntity).toList();

        PassReview passReview = new PassReview(
                passReviewSaveReq.getTitle(),
                passReviewSaveReq.getProcedureType(),
                passReviewSaveReq.getInterviewType(),
                passReviewSaveReq.getInterviewRatioType(),
                passReviewSaveReq.getInterviewMood(),
                club,
                member
        );

        for (PassReviewNote passReviewNote : passReviewNotes) {
            passReviewNote.setPassReview(passReview);
        }

        return passReview;
    }
}

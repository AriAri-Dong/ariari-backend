package com.ariari.ariari.domain.club.passreview.dto.req;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.dto.PassReviewNoteData;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.recruitment.enums.ProcedureType;
import lombok.Data;

import java.util.List;

@Data
public class PassReviewSaveReq {
    private String title;
    private ProcedureType procedureType;
    private InterviewType interviewType;
    private InterviewRatioType interviewRatioType;
    private Integer interviewMood;
    private List<PassReviewNoteData> documentPassReviewNotes;
    private List<PassReviewNoteData> interviewPassReviewNotes;

    public PassReview toEntity(ClubMember clubMember){
        return new PassReview(
                title,
                procedureType,
                interviewType,
                interviewRatioType,
                interviewMood,
                clubMember
        );
    }
}

package com.ariari.ariari.domain.club.passreview.dto.req;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.club.passreview.note.dto.req.PassReviewNoteReq;
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
    private List<PassReviewNoteReq> passReviewNotes;

    public PassReview toEntity(ClubMember clubMember){
        List<PassReviewNote> passReviewNotes = this.passReviewNotes.stream().map(PassReviewNoteReq::toEntity).toList();

        PassReview passReview = new PassReview(
                title,
                procedureType,
                interviewType,
                interviewRatioType,
                interviewMood,
                clubMember
        );

        for (PassReviewNote passReviewNote : passReviewNotes) {
            passReviewNote.setPassReview(passReview);
        }

        return passReview;
    }
}

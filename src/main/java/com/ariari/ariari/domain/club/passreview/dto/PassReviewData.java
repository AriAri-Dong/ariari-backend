package com.ariari.ariari.domain.club.passreview.dto;

import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.recruitment.enums.ProcedureType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class PassReviewData {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "합격후기 id", example = "")
    private Long id;
    @Schema(description = "합격후기 제목", example = "")
    private String title;
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "작성자 id", example = "")
    private Long creatorId;
    @Schema(description = "합격전형 DOCUMENT(서류) INTERVIEW(서류+면접)", example = "")
    private ProcedureType procedureType;
    @Schema(description = "면접방식", example = "")
    private InterviewType interviewType;
    @Schema(description = "면접방식(비율)", example = "")
    private InterviewRatioType interviewRatioType;
    @Schema(description = "면접 분위기", example = "")
    private Integer interviewMood;
    @Schema(description = "서류 전형 관련 문항들", example = "")
    private List<PassReviewNoteData> documentNotes;
    @Schema(description = "면접 전형 관련 문항들", example = "")
    private List<PassReviewNoteData> interviewNotes;

    @JsonIgnore
    @Schema(description = "서류 관련 문항 개수", example = "")
    private int documentNoteCount;

    @JsonIgnore
    @Schema(description = "면접 전형 관련 개수", example = "")
    private int interviewNoteCount;
    @Schema(description = "작성일자 localDateTime, ...", example = "")
    private LocalDateTime createdDateTime;

    public static List<PassReviewData> fromEntities(Member reqMember, List<PassReview> passReviews) {
        List<PassReviewData> passReviewDataList = new ArrayList<>();
        for (PassReview passReview : passReviews) {
            passReviewDataList.add(fromEntity(reqMember, passReview));
        }
        return passReviewDataList;
    }

    // 디테일용, 어차피 수정기능이 없어서 reqMember가 필요없음
    public static PassReviewData fromEntity(PassReview passReview, List<PassReviewNoteData> documentNotes, List<PassReviewNoteData> interviewNotes) {
        return PassReviewData.builder()
                .id(passReview.getId())
                .title(passReview.getTitle())
                .creatorId(passReview.getMember().getId())
                .procedureType(passReview.getProcedureType())
                .interviewType(passReview.getInterviewType())
                .interviewRatioType(passReview.getInterviewRatioType())
                .interviewMood(passReview.getInterviewMood())
                .documentNotes(documentNotes)
                .interviewNotes(interviewNotes)
                .build();
    }

    // 리스트용
    public static PassReviewData fromEntity(Member reqMember, PassReview passReview) {
        return PassReviewData.builder()
                .id(passReview.getId())
                .title(passReview.getTitle())
                .documentNoteCount(getPassReviewNoteCount(passReview, NoteType.DOCUMENT))
                .interviewNoteCount(getPassReviewNoteCount(passReview, NoteType.INTERVIEW))
                .build();
    }

    public static int getPassReviewNoteCount(PassReview passReview, NoteType noteType) {
        int count = 0;
        List<PassReviewNote> passReviewNotes = passReview.getPassReviewNotes();
        for (PassReviewNote passReviewNote : passReviewNotes){
            if(passReviewNote.getNoteType().equals(noteType)){
                count += 1;
            }
        }
        return count;
    }
}

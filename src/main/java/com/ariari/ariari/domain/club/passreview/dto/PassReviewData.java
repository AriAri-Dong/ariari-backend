package com.ariari.ariari.domain.club.passreview.dto;

import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.access.PassReviewAccess;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.enums.ProcedureType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PassReviewData {
    @Schema(description = "합격후기 id", example = "id값")
    private Long id;
    @Schema(description = "합격후기 제목", example = "제목")
    private String title;
    @Schema(description = "합격전형", example = "DOCUMENT(서류) INTERVIEW(서류+면접)")
    private ProcedureType procedureType;
    @Schema(description = "면접방식", example = "온라인 / 대면")
    private InterviewType interviewType;
    @Schema(description = "면접방식(비율)", example = "1 : 1 / 지원자1 : 면접관N / 지원자M : 면접관N")
    private InterviewRatioType interviewRatioType;
    @Schema(description = "면접 분위기", example = "1(편안한) ~ 5(엄숙한)")
    private Integer interviewMood;
    @Schema(description = "접근 가능 여부", example = "과거에 포인트를 사용하여 열람한 적이 있다면 편하게 열람 가능")
    private boolean wasAccessed;
    @Schema(description = "작성자 본인인지 여부", example = "작성자는 본인이 열람 가능해야함")
    private boolean isCreator;
    @Schema(description = "서류 전형 관련 문항들", example = "질문과 문항만 담겨 있음")
    private List<PassReviewNoteData> documentNotes;
    @Schema(description = "면접 전형 관련 문항들", example = "질문과 문항만 담겨 있음")
    private List<PassReviewNoteData> interviewNotes;
    @Schema(description = "서류 관련 문항 개수", example = "")
    private int documentNoteCount;
    @Schema(description = "면접 전형 관련 개수", example = "질문과 문항만 담겨 있음")
    private int interviewNoteCount;
    @Schema(description = "작성일자", example = "localDateTime, ...")
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
                .wasAccessed(getAccessPassReviews(reqMember).contains(passReview))
                .isCreator(reqMember.equals(passReview.getClubMember().getMember()))
                .documentNoteCount(getPassReviewNoteCount(passReview, NoteType.DOCUMENT))
                .interviewNoteCount(getPassReviewNoteCount(passReview, NoteType.INTERVIEW))
                .build();
    }

    public static Set<PassReview> getAccessPassReviews(Member reqMember) {
        if (reqMember == null) {
            return new HashSet<>();
        } else{
            return reqMember.getPassReviewAccessList()
                    .stream()
                    .map(PassReviewAccess::getPassReview)
                    .collect(Collectors.toSet());
        }
    }

    // 이거 LAZY로 계속 검색되는 코드인가...?
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

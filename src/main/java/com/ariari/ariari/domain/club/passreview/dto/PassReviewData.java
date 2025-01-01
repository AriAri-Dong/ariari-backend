package com.ariari.ariari.domain.club.passreview.dto;

import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.access.PassReviewAccess;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ariari.ariari.domain.club.passreview.dto.PassReviewNoteData.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PassReviewData {
    @Schema(description = "합격후기 id", example = "id값")
    private Long id;
    @Schema(description = "합격후기 제목", example = "제목")
    private String title;
    @Schema(description = "면접방식", example = "온라인 / 대면")
    private InterviewType interviewType;
    @Schema(description = "면접방식(비율)", example = "1 : 1 / 지원자1 : 면접관N / 지원자M : 면접관N")
    private InterviewRatioType interviewRatioType;
    @Schema(description = "면접 분위기", example = "1(편안한) ~ 5(엄숙한)")
    private Integer interviewMood;
    @Schema(description = "접근 가능 여부", example = "과거에 포인트를 사용하여 열람한 적이 있다면 편하게 열람 가능")
    private boolean wasAccessed;
    @Schema(description = "작성자 id", example = "작성자 id, 작성자는 본인이 열람 가능해야함")
    private Long memberId;
    @Schema(description = "작성자 닉네임", example = "일단 필요한지 안필요한지 모르겠어서 추가") // 애매한 부분
    private String nickname;
    @Schema(description = "서류 전형 관련 문항들", example = "질문과 문항만 담겨 있음")
    private List<PassReviewNoteData> documentNotes;
    @Schema(description = "면접 전형 관련 문항들", example = "질문과 문항만 담겨 있음")
    private List<PassReviewNoteData> interviewNotes;
    @Schema(description = "서류 관련 문항 개수", example = "")
    private Integer documentNoteCount;
    @Schema(description = "면접 전형 관련 문항들", example = "질문과 문항만 담겨 있음")
    private Integer interviewNoteCount;
    @Schema(description = "작성일자", example = "localDateTime, ...")
    private LocalDateTime createdDateTime;

    public static List<PassReviewData> fromEntities(Member reqMember, List<PassReview> passReviews) {
        List<PassReviewData> passReviewDataList = new ArrayList<>();
//        for (PassReview passReview : passReviews) {
//            passReviewDataList.add(fromEntity(reqMember, passReview, ));
//        }
        return passReviewDataList;
    }


    public static PassReviewData fromEntity(Member reqMember, PassReview passReview, List<PassReviewNoteData> documentNotes, List<PassReviewNoteData> interviewNotes) {
        return PassReviewData.builder()
                .id(passReview.getId())
                .title(passReview.getTitle())
                .interviewType(passReview.getInterviewType())
                .interviewRatioType(passReview.getInterviewRatioType())
                .interviewMood(passReview.getInterviewMood())
                .nickname(passReview.getClubMember().getMember().getNickName())
                .documentNotes(documentNotes)
                .interviewNotes(interviewNotes)
                .build();
    }

    // 다시 만들어야함
    public static PassReviewData fromEntity(Member reqMember, PassReview passReview) {
        return PassReviewData.builder()
                .id(passReview.getId())
                .title(passReview.getTitle())
                .wasAccessed(getAccessPassReviews(reqMember).contains(passReview))
                .memberId(passReview.getClubMember().getMember().getId())
                .nickname(passReview.getClubMember().getMember().getNickName())
                .documentNoteCount(1)
                .interviewNoteCount(1)
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

}

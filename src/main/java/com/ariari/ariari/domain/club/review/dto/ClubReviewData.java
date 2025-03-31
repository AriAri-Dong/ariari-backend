package com.ariari.ariari.domain.club.review.dto;

import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ClubReviewData {
    @Schema(description = "활동후기 id", example = "")
    private String id;
    @Schema(description = "활동후기 제목", example = "제목")
    private String title;
    @Schema(description = "활동후기 내용", example = "내용")
    private String body;
    @Schema(description = "작성자 닉네임", example = "테스트유저1")
    private String nickname;
    @Schema(description = "작성자 id", example = "")
    private String creatorId;
    @Schema(description = "작성일", example = "")
    private LocalDateTime createdDateTime;
    @Schema(description = "태그 데이터 CAREER_PREPARATION, NETWORKING, INTEREST_EXPLORATION, SELF_DEVELOPMENT, ACADEMIC_IMPROVEMENT, HEALTH_ENHANCEMENT, DIVERSE_EXPERIENCE", example = "")
    private List<TagData> tagDataList;

    // 상세 조회용
    public static ClubReviewData toClubReviewData(ClubReview clubReview, List<TagData> tagDataList){
        return ClubReviewData.builder()
                .id(Long.toString(clubReview.getId()))
                .title(clubReview.getTitle())
                .body(clubReview.getBody())
                .nickname(clubReview.getMember().getNickName())
                .creatorId(Long.toString(clubReview.getMember().getId()))
                .createdDateTime(clubReview.getCreatedDateTime())
                .tagDataList(tagDataList)
                .build();
    }

    // 목록 조회
    public static List<ClubReviewData> fromEntities(Page<ClubReview> clubReviews){
        List<ClubReviewData> clubReviewDataList = new ArrayList<>();
        for (ClubReview clubReview : clubReviews.getContent()) {
            clubReviewDataList.add(fromEntity(clubReview));
        }
        return clubReviewDataList;
    }

    // 목록 조회용
    public static ClubReviewData fromEntity(ClubReview clubReview){
        return ClubReviewData.builder()
                .id(Long.toString(clubReview.getId()))
                .title(clubReview.getTitle())
                .createdDateTime(clubReview.getCreatedDateTime())
                .build();
    }
}

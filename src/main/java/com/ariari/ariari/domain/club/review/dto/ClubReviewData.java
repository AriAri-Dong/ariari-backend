package com.ariari.ariari.domain.club.review.dto;

import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.access.ClubReviewAccess;
import com.ariari.ariari.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ClubReviewData {
    @Schema(description = "활동후기 id", example = "id값")
    private Long id;
    @Schema(description = "활동후기 제목", example = "제목")
    private String title;
    @Schema(description = "활동후기 내용", example = "내용")
    private String body;
    @Schema(description = "접근 가능 여부", example = "과거에 포인트를 사용하여 열람한 적이 있다면 편하게 열람 가능")
    private boolean wasAccessed;
    @Schema(description = "작성자 본인인지 여부", example = "작성자는 본인이 열람 가능해야함")
    private boolean isCreator;
    @Schema(description = "작성일", example = "작성일")
    private LocalDateTime createdDateTime;
    @Schema(description = "태그 데이터", example = "CAREER_PREPARATION, NETWORKING, INTEREST_EXPLORATION, SELF_DEVELOPMENT, ACADEMIC_IMPROVEMENT, HEALTH_ENHANCEMENT, DIVERSE_EXPERIENCE")
    private List<TagData> tagDataList;

    // 상세 조회용
    public static ClubReviewData toClubReviewData(ClubReview clubReview, List<TagData> tagDataList){
        return ClubReviewData.builder()
                .id(clubReview.getId())
                .title(clubReview.getTitle())
                .body(clubReview.getBody())
                .createdDateTime(clubReview.getCreatedDateTime())
                .tagDataList(tagDataList)
                .build();
    }

    // 목록 조회
    public static List<ClubReviewData> fromEntities(Page<ClubReview> clubReviews, Member reqMember){
        List<ClubReviewData> clubReviewDataList = new ArrayList<>();
        for (ClubReview clubReview : clubReviews.getContent()) {
            clubReviewDataList.add(fromEntity(clubReview, reqMember));
        }
        return clubReviewDataList;
    }

    // 목록 조회용
    public static ClubReviewData fromEntity(ClubReview clubReview, Member reqMember){
        return ClubReviewData.builder()
                .id(clubReview.getId())
                .title(clubReview.getTitle())
                .wasAccessed(getAccessClubReviews(reqMember).contains(clubReview))
                .isCreator(clubReview.getClubMember().getMember().equals(reqMember))
                .createdDateTime(clubReview.getCreatedDateTime())
                .build();
    }

    // 접근 권한 검사 해시맵
    public static Set<ClubReview> getAccessClubReviews(Member reqMember) {
        if (reqMember == null) {
            return new HashSet<>();
        } else{
            return reqMember.getClubReviewAccessList()
                    .stream()
                    .map(ClubReviewAccess::getClubReview)
                    .collect(Collectors.toSet());
        }
    }
}

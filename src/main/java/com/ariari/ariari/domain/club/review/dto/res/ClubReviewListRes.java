package com.ariari.ariari.domain.club.review.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewListRes;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewRes;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.dto.ClubReviewData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubReviewListRes {
    @Schema(description = "데이터, 활동후기 목록 관련 데이터", example = "")
    private List<ClubReviewData> contents;

    @Schema(description = "페이지 관련 정보", example = "")
    private PageInfo pageInfo;

    public static ClubReviewListRes toClubReviewResList(List<ClubReviewData> contents, Page<ClubReview> clubReviews) {
        return new ClubReviewListRes(
                contents,
                PageInfo.fromPage(clubReviews)
        );
    }
}

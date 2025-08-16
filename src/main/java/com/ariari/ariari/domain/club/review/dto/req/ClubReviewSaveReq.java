package com.ariari.ariari.domain.club.review.dto.req;

import com.ariari.ariari.commons.entity.Club;
import com.ariari.ariari.commons.entity.ClubReview;
import com.ariari.ariari.domain.club.review.enums.Icon;
import com.ariari.ariari.commons.entity.ClubReviewTag;
import com.ariari.ariari.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ClubReviewSaveReq {
    @Schema(description = "활동후기 제목", example = "제목")
    private String title;
    @Schema(description = "활동후기 내용", example = "내용")
    private String body;
    @Schema(description = "태그 리스트")
    private List<Icon> icons;

    public ClubReview toEntity(ClubReviewSaveReq clubReviewSaveReq, Member member, Club club, List<ClubReviewTag> clubReviewTags) {
        ClubReview clubReview = new ClubReview(
                clubReviewSaveReq.getTitle(),
                clubReviewSaveReq.getBody(),
                club,
                member);

        for(ClubReviewTag clubReviewTag : clubReviewTags) {
            clubReviewTag.setClubReview(clubReview);
            clubReview.getClubReviewTags().add(clubReviewTag);
        }

        return clubReview;
    }
}

package com.ariari.ariari.domain.club.review.dto.req;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.enums.Icon;
import com.ariari.ariari.domain.club.review.reviewtag.ClubReviewTag;
import com.ariari.ariari.domain.club.review.tag.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ClubReviewSaveReq {
    @Schema(description = "활동후기 제목", example = "제목")
    private String title;
    @Schema(description = "활동후기 내용", example = "내용")
    private String body;
    @Schema(description = "태그 리스트", example = "다양한 경험을 할 수 있어요, 전공 실력이나 성적을 높일 수 있어요...")
    private List<Icon> icons;

    public ClubReview toEntity(ClubMember clubMember, List<Tag> tags) {
        ClubReview clubReview = new ClubReview(
                title,
                body,
                clubMember);

        for (Tag tag : tags) {
            ClubReviewTag clubReviewTag = new ClubReviewTag();
            clubReviewTag.setTag(tag);
            clubReviewTag.setClubReview(clubReview);
        }

        return clubReview;
    }
}

package com.ariari.ariari.domain.club.review;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.review.dto.ClubReviewData;
import com.ariari.ariari.domain.club.review.dto.TagData;
import com.ariari.ariari.domain.club.review.dto.req.ClubReviewSaveReq;
import com.ariari.ariari.domain.club.review.dto.res.ClubReviewListRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping("/club-review")
@RequiredArgsConstructor
public class ClubReviewController {
    private final ClubReviewService clubReviewService;
    // 합격후기에서 중복작성도 막아야함 ★★★★★★★★★★★★★★★★★★★★★★

    @GetMapping("/{clubId}")
    @Operation(summary = "활동 후기 목록 조회", description = "활동 후기 목록을 조회합니다. " +
            "page 는 현재 page, size는 page에 들어가는 page size입니다.")
    public ClubReviewListRes search_club_review_page(Pageable pageable,
                                                     @PathVariable(name = "clubId") Long clubId){
        return clubReviewService.searchClubReviewPage(clubId, pageable);
    }

    @GetMapping("/detail/{clubReviewId}")
    @Operation(summary = "활동 후기 상세 조회", description = "활동 후기 상세 조회")
    public ClubReviewData find_club_review_detail(@PathVariable(name = "clubReviewId") Long clubReviewId){
        return clubReviewService.findClubReviewDetail(clubReviewId);
    }

    @GetMapping("/{clubId}/tag-statistics")
    @Operation(summary = "클럽 별 활동후기 태그 목록 조회", description = "클럽 별 활동후기 태그 목록 조회")
    public List<TagData> find_tag_statistics_at_club(@PathVariable(name = "clubId") Long clubId){
        return clubReviewService.findTagStatisticsAtClub(clubId);
    }

    @GetMapping("/tag-data")
    @Operation(summary = "활동후기에서 사용하는 태그데이터 조회", description = "활동후기 작성 시 유저에게 제공가능한 태그데이터" +
            "정보 제공하기")
    public List<TagData> search_club_review_tag(){
        return clubReviewService.searchClubReviewTag();
    }

    @PostMapping("/{clubId}")
    @Operation(summary = "활동후기 작성하기", description = "활동후기 작성하기")
    public void save_club_review(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @RequestBody ClubReviewSaveReq clubReviewSaveReq,
                                 @PathVariable(name = "clubId") Long clubId){
        Long reqMemberId = getMemberId(userDetails, false);
        clubReviewService.saveClubReview(reqMemberId, clubReviewSaveReq, clubId);
    }
}

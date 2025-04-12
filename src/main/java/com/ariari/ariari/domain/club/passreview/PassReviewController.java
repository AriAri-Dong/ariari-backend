package com.ariari.ariari.domain.club.passreview;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.passreview.dto.PassReviewData;
import com.ariari.ariari.domain.club.passreview.dto.req.PassReviewSaveReq;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewListRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping("/pass-review")
@RequiredArgsConstructor
public class PassReviewController {
    private final PassReviewService passReviewService;
    @GetMapping("/{clubId}")
    @Operation(summary = "합격 후기 목록 조회", description = "합격 후기 목록을 조회합니다. " +
            "page 는 현재 page, size는 page에 들어가는 page size입니다.")
    public PassReviewListRes search_pass_review_page(Pageable pageable,
                                                  @PathVariable(name = "clubId") Long clubId){
        return passReviewService.searchPassReviewPage(clubId, pageable);
    }

    @GetMapping("/detail/{passReviewId}")
    @Operation(summary = "합격 후기 상세 조회", description = "합격 후기 상세 내용을 조회합니다.")
    public PassReviewData find_pass_review_detail(@PathVariable(name = "passReviewId") Long passReviewId){
        return passReviewService.findPassReviewDetail(passReviewId);
    }

    @PostMapping("/{clubId}")
    @Operation(summary = "합격 후기 입력", description = "합격 후기 데이터를 생성한다.")
    public void save_pass_review(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @RequestBody PassReviewSaveReq passReviewSaveReq,
                                 @PathVariable(name = "clubId") Long clubId){
        Long reqMemberId = getMemberId(userDetails, false);
        passReviewService.savePassReview(reqMemberId, passReviewSaveReq, clubId);
    }

}

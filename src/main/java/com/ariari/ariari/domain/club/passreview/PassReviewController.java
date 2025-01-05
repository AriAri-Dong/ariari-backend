package com.ariari.ariari.domain.club.passreview;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.passreview.dto.PassReviewData;
import com.ariari.ariari.domain.club.passreview.dto.req.PassReviewSaveReq;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewListRes;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping("/pass-review")
@RequiredArgsConstructor
public class PassReviewController {
    private final PassReviewService passReviewService;

    // 패스리뷰 리스트
    // 필요한 요소 : 페이지네이션으로 id, 제목, 작성일자, 서류 관련 답변 개수, 면접관련 답변개수, 유저의 열람 여부

    // 패스 디테일
    // 필요한 요소 : 제목, 서류 or 서류면접 , 서류문항들(제목,답변), 면접방식, 면접인원, 면접분위기, 면접문항들(제목,답변)

    // 포인트쪽 처리 도메인 왜냐면 포인트를 처리해야함~

    // 합격후기 등록하기 -> 권한 검사

    @GetMapping("/{clubId}")
    @Operation(summary = "합격 후기 목록 조회", description = "합격 후기 목록을 조회합니다. " +
            "page 는 현재 page, size는 page에 들어가는 page size입니다.")
    public PassReviewListRes search_pass_review_page(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  Pageable pageable,
                                                  @PathVariable(name = "clubId") Long clubId){
        Long reqMemberId = getMemberId(userDetails, false);
        return passReviewService.searchPassReviewPage(reqMemberId, clubId, pageable);
    }

    @GetMapping("/{passReviewId}")
    @Operation(summary = "합격 후기 상세 조회", description = "합격 후기 상세 내용을 조회합니다.")
    public PassReviewData find_pass_review_detail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @PathVariable(name = "passReviewId") Long passReviewId){
        //Long reqMemberId = getMemberId(userDetails, false);
        return passReviewService.findPassReviewDetail(passReviewId);
    }

    @PostMapping("/access/{passReviewId}")
    @Operation(summary = "합격 후기 접근 권한 생성", description = "포인트를 사용하여 해당 합격후기를 열람할 시" +
            "합격후기를 열람할 수 있는 권한 데이터를 생성합니다.")
    public void access_pass_review_detail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable(name = "passReviewId") Long passReviewId){
        Long reqMemberId = getMemberId(userDetails, false);
        passReviewService.accessPassReivew(reqMemberId, passReviewId);
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

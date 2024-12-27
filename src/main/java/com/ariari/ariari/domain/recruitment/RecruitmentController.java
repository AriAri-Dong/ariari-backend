package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.recruitment.dto.req.RecruitmentSaveReq;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentDetailRes;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentListRes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final RecruitmentListService recruitmentListService;

    // 상세 조회
    @GetMapping("/recruitments/{recruitmentId}")
    public RecruitmentDetailRes findRecruitmentDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable Long recruitmentId,
                                                      HttpServletRequest request) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, false);
        String clientIp = ViewsManager.getClientIp(request);
        return recruitmentService.findRecruitmentDetail(reqMemberId, recruitmentId, clientIp);
    }

    // 등록
    @PostMapping("/clubs/{clubId}/recruitments")
    public void saveRecruitment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long clubId,
                                @RequestPart RecruitmentSaveReq saveReq,
                                @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        recruitmentService.saveRecruitment(reqMemberId, clubId, saveReq, file);
    }

    // 모집 마감

    // 삭제
    @DeleteMapping("/recruitments/{recruitmentId}")
    public void removeRecruitment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable Long recruitmentId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        recruitmentService.removeRecruitment(reqMemberId, recruitmentId);
    }

    @GetMapping("/recruitments")
    public RecruitmentListRes findRecruitmentPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @ModelAttribute ClubSearchCondition condition,
                                                  Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentListService.searchRecruitmentPage(reqMemberId, condition, pageable);
    }

    @GetMapping("/recruitments/external")
    public RecruitmentListRes findExternalPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @ModelAttribute ClubSearchCondition condition,
                                                          Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentListService.searchExternalPage(reqMemberId, condition, pageable);
    }

    @GetMapping("/recruitments/internal")
    public RecruitmentListRes findInternalPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @ModelAttribute ClubSearchCondition condition,
                                                          Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return recruitmentListService.searchInternalPage(reqMemberId, condition, pageable);
    }

    // 북마크 리스트 조회

    // 동아리의 리스트 조회

    // 교외 랭킹 리스트 조회

    // 교내 랭킹 리스트 조회

}

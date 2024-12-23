package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
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
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        String clientIp = ViewsManager.getClientIp(request);
        return recruitmentService.findRecruitmentDetail(memberId, recruitmentId, clientIp);
    }

    // 등록
    @PostMapping("/clubs/{clubId}/recruitments")
    public void saveRecruitment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long clubId,
                                @RequestPart RecruitmentSaveReq saveReq,
                                @RequestPart(required = false) MultipartFile file) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        recruitmentService.saveRecruitment(memberId, clubId, saveReq, file);
    }

    // 모집 마감

    // 삭제

    @GetMapping("/external")
    public RecruitmentListRes findExternalRecruitmentList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                                         Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentListService.findExternalList(memberId, clubCategoryType, pageable);
    }

    @GetMapping("/internal")
    public RecruitmentListRes findInternalRecruitmentList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                                      Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        return recruitmentListService.findInternalList(memberId, clubCategoryType, pageable);
    }

}

package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentListService recruitmentListService;

    @GetMapping("/external")
    public RecruitmentListRes externalRecruitmentList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                                         Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentListService.findExternalList(memberId, clubCategoryType, pageable);
    }

    @GetMapping("/internal")
    public RecruitmentListRes internalRecruitmentList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                                      Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        return recruitmentListService.findInternalList(memberId, clubCategoryType, pageable);
    }

}

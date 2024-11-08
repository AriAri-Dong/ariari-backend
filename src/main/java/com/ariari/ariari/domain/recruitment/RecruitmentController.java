package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @GetMapping("/ranking")
    public List<RecruitmentData> recruitmentRanking(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    ClubAffiliationType clubAffiliationType) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentService.findRecruitmentRankingList(memberId, clubAffiliationType);
    }

    @GetMapping("/latest")
    public List<RecruitmentData> recruitmentList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 ClubAffiliationType clubAffiliationType,
                                                 @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                                 Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentService.findRecruitmentList(memberId, clubAffiliationType, clubCategoryType, pageable);
    }

}

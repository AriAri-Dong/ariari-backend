package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.dto.ClubMiniData;
import com.ariari.ariari.domain.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/ranking")
    public List<ClubMiniData> clubRankingList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              ClubAffiliationType clubAffiliationType,
                                              @RequestParam(required = false) ClubCategoryType clubCategoryType) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return clubService.findClubRankingList(memberId, clubAffiliationType, clubCategoryType);
    }

}

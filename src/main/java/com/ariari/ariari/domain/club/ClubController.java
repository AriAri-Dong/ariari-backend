package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.dto.ClubListRes;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubListService clubListService;

    @GetMapping("/external")
    public ClubListRes ExternalClubList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                       Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return clubListService.findExternalList(memberId, clubCategoryType, pageable);
    }

    @GetMapping("/internal")
    public ClubListRes InternalClubList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                        Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubListService.findInternalList(memberId, clubCategoryType, pageable);
    }

}

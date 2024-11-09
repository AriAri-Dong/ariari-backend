package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.dto.ClubDetailRes;
import com.ariari.ariari.domain.club.dto.ClubListRes;
import com.ariari.ariari.domain.club.dto.ClubModifyReq;
import com.ariari.ariari.domain.club.dto.ClubSaveReq;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubListService clubListService;
    private final ClubService clubService;

    @GetMapping("/external")
    public ClubListRes findExternalList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                        Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return clubListService.findExternalList(memberId, clubCategoryType, pageable);
    }

    @GetMapping("/internal")
    public ClubListRes findInternalList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                        Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubListService.findInternalList(memberId, clubCategoryType, pageable);
    }

    @GetMapping("/my/external")
    public ClubListRes findMyExternalList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubListService.findMyExternalList(memberId, pageable);
    }

    @GetMapping("/my/internal")
    public ClubListRes findMyInternalList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          Pageable pageable) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubListService.findMyInternalList(memberId, pageable);
    }

    @GetMapping("/{clubId}")
    public ClubDetailRes findClubDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long clubId,
                                        HttpServletRequest request) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);

        String clientIp = ViewsManager.getClientIp(request);

        return clubService.findClubDetail(memberId, clubId, clientIp);
    }

    @PostMapping
    public void saveClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @RequestBody ClubSaveReq saveReq) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        clubService.saveClub(memberId, saveReq);
    }

    @PatchMapping("/{clubId}")
    public void modifyClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId,
                           @RequestBody ClubModifyReq modifyReq) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        clubService.modifyClub(memberId, clubId, modifyReq);
    }

    @DeleteMapping("/{clubId}")
    public void removeClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId) {
        Long memberId = CustomUserDetails.getMemberId(userDetails, true);
        clubService.removeClub(memberId, clubId);
    }

}

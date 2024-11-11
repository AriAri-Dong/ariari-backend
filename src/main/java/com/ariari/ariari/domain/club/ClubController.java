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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.*;

@Slf4j
@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubListService clubListService;
    private final ClubService clubService;

    @GetMapping
    public ClubListRes findClubList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                    Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        log.info("!!!~!~ {} {}", userDetails, reqMemberId);
        return clubListService.findClubList(reqMemberId, clubCategoryType, pageable);
    }

    @GetMapping("/external")
    public ClubListRes findExternalList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                        Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.findExternalList(reqMemberId, clubCategoryType, pageable);
    }

    @GetMapping("/internal")
    public ClubListRes findInternalList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(required = false) ClubCategoryType clubCategoryType,
                                        Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findInternalList(reqMemberId, clubCategoryType, pageable);
    }

    @GetMapping("/my")
    public ClubListRes findMyClubList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findMyClubList(reqMemberId, pageable);
    }

    @GetMapping("/my-bookmarks")
    public ClubListRes findMyBookmarkClubList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findMyBookmarkClubsList(reqMemberId, pageable);
    }

    @GetMapping("/search")
    public ClubListRes findClubListBySearch(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam String query,
                                        Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.findClubListBySearch(reqMemberId, query, pageable);
    }

    @GetMapping("/{clubId}")
    public ClubDetailRes findClubDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long clubId,
                                        HttpServletRequest request) {
        Long reqMemberId = getMemberId(userDetails, false);

        String clientIp = ViewsManager.getClientIp(request);

        return clubService.findClubDetail(reqMemberId, clubId, clientIp);
    }

    @PostMapping
    public void saveClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @RequestBody ClubSaveReq saveReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubService.saveClub(reqMemberId, saveReq);
    }

    @PatchMapping("/{clubId}")
    public void modifyClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId,
                           @RequestBody ClubModifyReq modifyReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubService.modifyClub(reqMemberId, clubId, modifyReq);
    }

    @DeleteMapping("/{clubId}")
    public void removeClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubService.removeClub(reqMemberId, clubId);
    }

}

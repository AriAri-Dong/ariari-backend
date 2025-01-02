package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.dto.req.ClubModifyReq;
import com.ariari.ariari.domain.club.dto.req.ClubSaveReq;
import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.dto.res.ClubDetailRes;
import com.ariari.ariari.domain.club.dto.res.ClubListRes;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.*;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubListService clubListService;
    private final ClubService clubService;

    @GetMapping
    public ClubListRes searchClubPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @ModelAttribute ClubSearchCondition condition,
                                      Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.searchClubPage(reqMemberId, condition, pageable);
    }

    @GetMapping("/external")
    public ClubListRes searchExternalPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @ModelAttribute ClubSearchCondition condition,
                                          Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.searchExternalPage(reqMemberId, condition, pageable);
    }

    @GetMapping("/internal")
    public ClubListRes searchInternal(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @ModelAttribute ClubSearchCondition condition,
                                      Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.searchInternalPage(reqMemberId, condition, pageable);
    }

    @GetMapping("/my")
    public ClubListRes findMyClubList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findMyClubList(reqMemberId);
    }

    @GetMapping("/my-bookmarks")
    public ClubListRes findMyBookmarkClubList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findMyBookmarkClubsList(reqMemberId, pageable);
    }

    @GetMapping("/search")
    public ClubListRes findClubListByWord(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestParam String query,
                                          Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.findClubListByWord(reqMemberId, query, pageable);
    }

    @GetMapping("/external/ranking")
    public ClubListRes findClubExternalRankingList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @RequestParam(required = false) ClubCategoryType categoryType) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.findExternalClubRankingList(reqMemberId, categoryType);
    }

    @GetMapping("/internal/ranking")
    public ClubListRes findClubInternalRankingList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @RequestParam(required = false) ClubCategoryType categoryType) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findInternalClubRankingList(reqMemberId, categoryType);
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
                         @RequestPart ClubSaveReq saveReq,
                         @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubService.saveClub(reqMemberId, saveReq, file);
    }

    /**
     * 수정 예정
     */
    @PatchMapping(value = "/{clubId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void modifyClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId,
                           @RequestPart ClubModifyReq modifyReq,
                           @RequestPart(required = false) MultipartFile profileFile,
                           @RequestPart(required = false) MultipartFile bannerFile) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubService.modifyClub(reqMemberId, clubId, modifyReq, profileFile, bannerFile);
    }

    @DeleteMapping("/{clubId}")
    public void removeClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubService.removeClub(reqMemberId, clubId);
    }

}

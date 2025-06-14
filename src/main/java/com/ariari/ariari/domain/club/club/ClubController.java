package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.exception.exceptions.UnsupportedMultipartFileTypeException;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.club.dto.req.ClubModifyReq;
import com.ariari.ariari.domain.club.club.dto.req.ClubSaveReq;
import com.ariari.ariari.domain.club.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.club.dto.res.ClubDetailRes;
import com.ariari.ariari.domain.club.club.dto.res.ClubListRes;
import com.ariari.ariari.domain.club.club.dto.res.MyClubListRes;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.*;
import static com.ariari.ariari.commons.manager.ValidateMultipartFileManager.isValidTypeFile;

@Tag(name = "club", description = "동아리 기능")
@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubListService clubListService;
    private final ClubService clubService;

    @Operation(summary = "전체 동아리 검색 조회", description = "전체 동아리 리스트를 검색 조건에 맞게 조회합니다. (교내 + 교외) (페이지네이션)")
    @GetMapping
    public ClubListRes searchClubPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @ModelAttribute ClubSearchCondition condition,
                                      Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.searchClubPage(reqMemberId, condition, pageable);
    }

    @Operation(summary = "교외 동아리 검색 조회", description = "교외 동아리 리스트를 검색 조건에 맞게 조회합니다. (페이지네이션)")
    @GetMapping("/external")
    public ClubListRes searchExternalPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @ModelAttribute ClubSearchCondition condition,
                                          Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.searchExternalPage(reqMemberId, condition, pageable);
    }

    @Operation(summary = "교내 동아리 검색 조회", description = "교내 동아리 리스트를 검색 조건에 맞게 조회합니다. (페이지네이션)")
    @GetMapping("/internal")
    public ClubListRes searchInternal(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @ModelAttribute ClubSearchCondition condition,
                                      Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.searchInternalPage(reqMemberId, condition, pageable);
    }

    @Operation(summary = "내 동아리 조회", description = "내가 속한 전체 동아리 리스트를 조회합니다.")
    @GetMapping("/my")
    public MyClubListRes findMyClubList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findMyClubList(reqMemberId);
    }

    @Operation(summary = "내 동아리 조회 (내가 최고관리자인)", description = "내가 속한 전체 동아리 중 내가 최고관리자인 동아리 리스트를 조회합니다.")
    @GetMapping("/my/admin")
    public ClubListRes findMyAdminClubList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findMyAdminClubList(reqMemberId);
    }

    @Operation(summary = "내 북마크 동아리 조회", description = "내가 북마크 등록한 동아리 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/my-bookmarks")
    public ClubListRes findMyBookmarkClubList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @RequestParam Boolean hasActiveRecruitment,
                                              Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findMyBookmarkClubsList(reqMemberId, hasActiveRecruitment, pageable);
    }

    @Operation(summary = "동아리 검색어 조회", description = "이름에 검색어가 포함되어 있는 동아리 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/search")
    public ClubListRes findClubListByWord(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestParam String query,
                                          Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.findClubListByWord(reqMemberId, query, pageable);
    }

    @Operation(summary = "교외 동아리 랭킹 조회 ", description = "조회 수 기준 랭킹 9위까지의 교외 동아리 리스트를 조회합니다.")
    @GetMapping("/external/ranking")
    public ClubListRes findClubExternalRankingList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @RequestParam(required = false) ClubCategoryType categoryType) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubListService.findExternalClubRankingList(reqMemberId, categoryType);
    }

    @Operation(summary = "교내 동아리 랭킹 조회", description = "조회 수 기준 랭킹 9위까지의 교내 동아리 리스트를 조회합니다.")
    @GetMapping("/internal/ranking")
    public ClubListRes findClubInternalRankingList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @RequestParam(required = false) ClubCategoryType categoryType) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubListService.findInternalClubRankingList(reqMemberId, categoryType);
    }

    @Operation(summary = "동아리 상세 조회", description = "")
    @GetMapping("/{clubId}")
    public ClubDetailRes findClubDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long clubId,
                                        HttpServletRequest request) {
        Long reqMemberId = getMemberId(userDetails, false);
        String clientIp = ViewsManager.getClientIp(request);

        return clubService.findClubDetail(reqMemberId, clubId, clientIp);
    }

    @Operation(summary = "동아리 등록", description = "")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @RequestPart ClubSaveReq saveReq,
                         @RequestPart(required = false) MultipartFile file) {
        if(!isValidTypeFile(file)){
            throw new UnsupportedMultipartFileTypeException();
        }

        Long reqMemberId = getMemberId(userDetails, true);
        clubService.saveClub(reqMemberId, saveReq, file);
    }

    @Operation(summary = "동아리 수정", description = "")
    @PutMapping(value = "/{clubId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void modifyClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId,
                           @RequestPart ClubModifyReq modifyReq,
                           @RequestPart(required = false) MultipartFile profileFile,
                           @RequestPart(required = false) MultipartFile bannerFile) {
        if(!isValidTypeFile(profileFile)){
            throw new UnsupportedMultipartFileTypeException();
        }

        Long reqMemberId = getMemberId(userDetails, true);
        clubService.modifyClub(reqMemberId, clubId, modifyReq, profileFile, bannerFile);
    }


    /**
     * 검증 로직 수정 예정
     */
    @Operation(summary = "동아리 폐쇄", description = "*** 검증 로직 수정 예정 ***")
    @DeleteMapping("/close/{clubId}")
    public void removeClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubService.removeClub(reqMemberId, clubId);
    }

    @Operation(summary = "동아리장인 교내 동아리 조회", description = "")
    @GetMapping("/my/admin/school")
    public ClubListRes findSchoolAdminClub(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long reqMemberId = getMemberId(userDetails, true);
        return clubService.findSchoolAdminClub(reqMemberId);
    }

}

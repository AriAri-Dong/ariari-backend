package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.recruitment.dto.req.RecruitmentSaveReq;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentDetailRes;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "recruitment", description = "모집 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final RecruitmentListService recruitmentListService;

    @Operation(summary = "모집 상세 조회", description = "모집을 상세 조회합니다. 모집의 조회 수가 상승합니다.")
    @GetMapping("/recruitments/{recruitmentId}")
    public RecruitmentDetailRes findRecruitmentDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable Long recruitmentId,
                                                      HttpServletRequest request) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, false);
        String clientIp = ViewsManager.getClientIp(request);
        return recruitmentService.findRecruitmentDetail(reqMemberId, recruitmentId, clientIp);
    }

    @Operation(summary = "모집 등록", description = "모집을 등록합니다. 동아리 관리자만이 가능합니다.")
    @PostMapping(value = "/clubs/{clubId}/recruitments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveRecruitment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long clubId,
                                @RequestPart RecruitmentSaveReq saveReq,
                                @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        recruitmentService.saveRecruitment(reqMemberId, clubId, saveReq, file);
    }

    @Operation(summary = "모집 마감", description = "모집을 조기 마감합니다. 동아리 관리자만이 가능합니다.")
    @PutMapping("/recruitments/{recruitmentId}")
    public void closeRecruitment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long recruitmentId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        recruitmentService.closeRecruitment(reqMemberId, recruitmentId);
    }

    @Operation(summary = "모집 삭제", description = "모집을 삭제합니다. 동아리 관리자만이 가능합니다.")
    @DeleteMapping("/recruitments/{recruitmentId}")
    public void removeRecruitment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable Long recruitmentId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        recruitmentService.removeRecruitment(reqMemberId, recruitmentId);
    }

    @Operation(summary = "전체 모집 리스트 검색 조회", description = "검색 조건에 따라 전체 모집(교내 + 교외) 리스트를 조회합니다. 현재 활성화된 모집 데이터만 조회합니다. (페이지네이션)")
    @GetMapping("/recruitments")
    public RecruitmentListRes findRecruitmentPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @ModelAttribute ClubSearchCondition condition,
                                                  Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentListService.searchRecruitmentPage(reqMemberId, condition, pageable);
    }

    @Operation(summary = "교외 모집 리스트 검색 조회", description = "검색 조건에 따라 교외 모집 리스트를 조회합니다. 현재 활성화된 모집 데이터만 조회합니다. (페이지네이션)")
    @GetMapping("/recruitments/external")
    public RecruitmentListRes findExternalPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @ModelAttribute ClubSearchCondition condition,
                                               Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentListService.searchExternalPage(reqMemberId, condition, pageable);
    }

    @Operation(summary = "교내 모집 리스트 검색 조회", description = "검색 조건에 따라 교내 모집 리스트를 조회합니다. 현재 활성화된 모집 데이터만 조회합니다. (페이지네이션)")
    @GetMapping("/recruitments/internal")
    public RecruitmentListRes findInternalPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @ModelAttribute ClubSearchCondition condition,
                                                          Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return recruitmentListService.searchInternalPage(reqMemberId, condition, pageable);
    }

    @Operation(summary = "내 북마크 모집 리스트 조회", description = "내가 북마크 등록한 모집 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/recruitments/my-bookmarks")
    public RecruitmentListRes findMyBookmarkRecruitmentList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return recruitmentListService.findMyBookmarkRecruitmentList(reqMemberId, pageable);
    }

    @Operation(summary = "동아리의 모집 리스트 조회", description = "동아리의 모집 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/clubs/{clubId}/recruitments")
    public RecruitmentListRes findRecruitmentListInClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @PathVariable Long clubId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return recruitmentListService.findRecruitmentListInClub(reqMemberId, clubId);
    }

    @Operation(summary = "교외 모집 랭킹 리스트 조회", description = "조회 수 기준 상위 9개의 교외 모집 리스트를 조회합니다.")
    @GetMapping("/recruitments/external/ranking")
    public RecruitmentListRes findExternalRankingList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, false);
        return recruitmentListService.findExternalRankingList(reqMemberId);
    }

    @Operation(summary = "교내 모집 랭킹 리스트 조회", description = "조회 수 기준 상위 9개의 교내 모집 리스트를 조회합니다.")
    @GetMapping("/recruitments/internal/ranking")
    public RecruitmentListRes findInternalRankingList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return recruitmentListService.findInternalRankingList(reqMemberId);
    }

}

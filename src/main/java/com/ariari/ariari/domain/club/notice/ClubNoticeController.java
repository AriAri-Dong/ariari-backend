package com.ariari.ariari.domain.club.notice;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeDetailRes;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeListRes;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeModifyReq;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeSaveReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "club_notice", description = "동아리 공지사항 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClubNoticeController {

    private final ClubNoticeService clubNoticeService;

    @Operation(summary = "동아리 공지사항 등록", description = "동아리 관리자만이 등록할 수 있습니다.")
    @PostMapping(value = "/clubs/{clubId}/club-notices", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveClubNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubId,
                               @RequestPart ClubNoticeSaveReq saveReq,
                               @RequestPart(required = false) List<MultipartFile> files) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubNoticeService.saveClubNotice(reqMemberId, clubId, saveReq, files);
    }

    @Operation(summary = "동아리 공지사항 수정", description = "동아리 관리자만이 수정할 수 있습니다.")
    @PostMapping(value = "/club-notices/{clubNoticeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void modifyClubNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubNoticeId,
                                 @RequestPart ClubNoticeModifyReq modifyReq,
                                 @RequestPart(required = false) List<MultipartFile> files) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubNoticeService.modifyClubNotice(reqMemberId, clubNoticeId, modifyReq, files);
    }

    @Operation(summary = "동아리 공지사항 삭제", description = "동아리 관리자만이 삭제할 수 있습니다.")
    @DeleteMapping("/club-notices/{clubNoticeId}")
    public void removeClubNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubNoticeId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubNoticeService.removeClubNotice(reqMemberId, clubNoticeId);
    }

    @Operation(summary = "동아리 공지사항 상단 고정 (해제)", description = "동아리 관리자만이 상단 고정할 수 있습니다. 이미 고정된 공지사항의 경우 고정이 해제됩니다.(온/오프 토글 방식) 상단 고정 내에서 순서 조정은 불가능합니다. 최대 3개의 공지사항을 고정할 수 있습니다.")
    @PostMapping("/club-notices/{clubNoticeId}/fix")
    public void toggleClubNoticeFix(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable Long clubNoticeId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubNoticeService.toggleClubNoticeFix(reqMemberId, clubNoticeId);
    }

    @Operation(summary = "동아리 공지사항 상세 조회", description = "동아리 회원만이 조회할 수 있습니다.")
    @GetMapping("/club-notices/{clubNoticeId}")
    public ClubNoticeDetailRes findClubNoticeDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @PathVariable Long clubNoticeId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubNoticeService.findClubNoticeDetail(reqMemberId, clubNoticeId);
    }

    @Operation(summary = "고정 동아리 공지사항 리스트 조회", description = "동아리 회원만이 조회할 수 있습니다.")
    @GetMapping("/clubs/{clubId}/club-notices/fix")
    public ClubNoticeListRes findFixedClubNotices(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @PathVariable Long clubId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubNoticeService.findFixedClubNotices(reqMemberId, clubId);
    }

    @Operation(summary = "동아리 공지사항 리스트 조회", description = "동아리 회원만이 조회할 수 있습니다. 고정 동아리 공지사항은 제외됩니다.(페이지네이션)")
    @GetMapping("/clubs/{clubId}/club-notices")
    public ClubNoticeListRes findClubNotices(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable Long clubId,
                                             Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubNoticeService.findClubNotices(reqMemberId, clubId, pageable);
    }

}

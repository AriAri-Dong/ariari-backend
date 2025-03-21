package com.ariari.ariari.domain.system;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.notice.ClubNoticeService;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeSaveReq;
import com.ariari.ariari.domain.system.dto.req.SystemNoticeModifyReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "system_notice", description = "서비스 공지사항 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SystemNoticeController {

    private final SystemNoticeService systemNoticeService;

    @Operation(summary = "서비스 공지사항 등록", description = "운영 관리자만이 등록할 수 있습니다.")
    @PostMapping(value = "/clubs/{clubId}/club-notices", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveClubNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubId,
                               @RequestPart ClubNoticeSaveReq saveReq,
                               @RequestPart(required = false) List<MultipartFile> files) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
    }
}



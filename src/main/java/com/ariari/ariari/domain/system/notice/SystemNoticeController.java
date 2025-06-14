package com.ariari.ariari.domain.system.notice;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.exception.exceptions.UnsupportedMultipartFileTypeException;
import com.ariari.ariari.domain.system.notice.dto.req.SystemNoticeModifyReq;
import com.ariari.ariari.domain.system.notice.dto.req.SystemNoticeSaveReq;
import com.ariari.ariari.domain.system.notice.dto.res.SystemNoticeDetailRes;
import com.ariari.ariari.domain.system.notice.dto.res.SystemNoticeListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ariari.ariari.commons.manager.ValidateMultipartFileManager.isValidTypeFileList;

@Tag(name = "system_notice", description = "서비스 공지사항 기능")
@RestController
@RequiredArgsConstructor
public class SystemNoticeController {

    private final SystemNoticeService systemNoticeService;

    @Operation(summary = "서비스 공지사항 리스트 조회", description = "운영 관리자만이 조회할 수 있습니다.")
    @GetMapping("/service-notices")
    public SystemNoticeListRes findSystemNotices() {
        return systemNoticeService.findSystemNotices();
    }

    @Operation(summary = "서비스 공지사항 등록", description = "운영 관리자만이 등록할 수 있습니다.")
    @PostMapping(value = "/service-notices/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveSystemNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestPart SystemNoticeSaveReq saveReq,
                               @RequestPart(required = false) List<MultipartFile> files) {
        if(!isValidTypeFileList(files)){
            throw new UnsupportedMultipartFileTypeException();
        }

        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        systemNoticeService.saveSystemNotice(saveReq, files);
    }

    @Operation(summary = "서비스 공지사항 수정", description = "운영 관리자만이 수정할 수 있습니다.")
    @PatchMapping(value = "/service-notices/{systemNoticeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void modifySystemNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long systemNoticeId,
                                 @RequestPart SystemNoticeModifyReq modifyReq,
                                 @RequestPart(required = false) List<MultipartFile> files) {
        if(!isValidTypeFileList(files)){
            throw new UnsupportedMultipartFileTypeException();
        }

        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        systemNoticeService.modifySystemNotice(reqMemberId, systemNoticeId, modifyReq, files);
    }

    @Operation(summary = "서비스 공지사항 삭제", description = "운영 관리자만이 삭제할 수 있습니다.")
    @DeleteMapping("/service-notices/{systemNoticeId}")
    public void removeSystemNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long systemNoticeId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        systemNoticeService.removeSystemNotice(reqMemberId, systemNoticeId);
    }


    @Operation(summary = "서비스 공지사항 상세 조회", description = " 모든 회원이 조회할 수 있습니다.")
    @GetMapping("/service-notices/{systemNoticeId}/detail")
    public SystemNoticeDetailRes findSystemNoticeDetail(@PathVariable Long systemNoticeId) {
        return systemNoticeService.findSystemNoticeDetail(systemNoticeId);
    }
}



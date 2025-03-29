package com.ariari.ariari.domain.club.activity.report;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "clubActivityReport", description = "동아리 활동 신고 기능")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ClubActivityReportController {

    private final ClubActivityReportService clubActivityReportService;


    @Operation(summary = "동아리 활동 신고", description = "회원 ID 중복이 불가능합니다. 만약 중복되는 ID일 경우 예외가 발생합니다.")
    @PostMapping("/clubActivitys")
    public void reportClubActivity(@Valid @RequestBody ReportReq reportClubActivityReq, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long reporterId = CustomUserDetails.getMemberId(customUserDetails, true);
        clubActivityReportService.reportClubActivity(reporterId,reportClubActivityReq);
    }
}

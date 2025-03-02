package com.ariari.ariari.domain.club.report;

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

@Tag(name = "clubReport", description = "동아리 신고 기능")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ClubReportController {

    private final ClubReportService clubReportService;

    @Operation(summary = "동아리 신고", description = "객체 ID 중복이 불가능합니다. 만약 중복되는 ID일 경우 예외가 발생합니다.")
    @PostMapping("/clubs")
    public void reportClub(@Valid @RequestBody ReportReq reportClubReq, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long reporterId = CustomUserDetails.getMemberId(customUserDetails, true);
        clubReportService.reportClub(reporterId,reportClubReq);
    }
}

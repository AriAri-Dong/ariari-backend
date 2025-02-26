package com.ariari.ariari.domain.member.report;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.member.dto.req.ReportMemberReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "memberReport", description = "회원 신고 기능")
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class MemberReportController {

    private final MemberReportService memberReportService;

    @Operation(summary = "회원 신고", description = "회원 ID 중복이 불가능합니다. 만약 중복되는 ID일 경우 예외가 발생합니다.")
    @PostMapping("/members")
    public ResponseEntity<Void> reportMember(@Valid @RequestBody ReportMemberReq reportMemberReq, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long reporterId = CustomUserDetails.getMemberId(customUserDetails, true);
        return memberReportService.reportMember(reporterId,reportMemberReq);
    }


}

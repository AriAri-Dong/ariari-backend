package com.ariari.ariari.domain.school.auth;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.school.auth.dto.req.SchoolAuthCodeReq;
import com.ariari.ariari.domain.school.auth.dto.req.SchoolAuthReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "school-auth", description = "학교 인증 기능")
@RestController
@RequestMapping("/schools/auth")
@RequiredArgsConstructor
public class SchoolAuthController {

    private final SchoolAuthService schoolAuthService;

    @Operation(summary = "학교 인증 이메일 발송 요청", description = "요청 body 로 받은 email 로 학교 인증 코드 발송을 요청합니다. 5분 내로 인증번호를 입력해야 합니다.")
    @PostMapping("/send")
    public void sendSchoolAuthCode(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestBody SchoolAuthReq schoolAuthReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        schoolAuthService.sendSchoolAuthCode(reqMemberId, schoolAuthReq);
    }

    @Operation(summary = "학교 인증 코드 검증", description = "요청 body 로 받은 학교 인증 코드를 검증합니다.")
    @PostMapping("/validate")
    public void validateSchoolAuthCode(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody SchoolAuthCodeReq schoolAuthCodeReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        schoolAuthService.validateSchoolAuthCode(reqMemberId, schoolAuthCodeReq);
    }

    @Operation(summary = "학교 인증 취소", description = "등록된 내 학교 인증을 취소합니다.")
    @PutMapping("/cancel")
    public void removeMySchoolAuth(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        schoolAuthService.removeMySchoolAuth(reqMemberId);
    }

}

package com.ariari.ariari.domain.school.auth;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools/auth")
@RequiredArgsConstructor
public class SchoolAuthController {

    private final SchoolAuthService schoolAuthService;

    @Operation(summary = "학교 인증 이메일 발송 요청", description = "요청 body 로 받은 email 로 학교 인증 코드 발송을 요청합니다. 5분 내로 인증번호를 입력해야 합니다.")
    @PostMapping("/send")
    public void sendSchoolAuthCode(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestBody String email) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        schoolAuthService.sendSchoolAuthCode(reqMemberId, email);
    }

    @Operation(summary = "학교 인증 코드 검증", description = "요청 body 로 받은 학교 인증 코드를 검증합니다.")
    @PostMapping("/validate")
    public void validateSchoolAuthCode(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody String schoolAuthCode) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        schoolAuthService.validateSchoolAuthCode(reqMemberId, schoolAuthCode);
    }

}

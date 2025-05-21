package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.*;
import com.ariari.ariari.commons.auth.exceptions.IllegalEmailException;
import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.auth.springsecurity.UnregisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth", description = "인증 관련 어노테이션")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoAuthManager kakaoAuthManager;
    private final UnregisterService unregisterService;

    /**
     * kakao login
     */
    @GetMapping("/login/kakao")
    @Operation(summary = "로그인", description = "카카오 로그인")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = JwtTokenRes.class)))
    public JwtTokenRes login(@RequestParam(name = "code") String code) {
        return authService.login(code);
    }

    @PostMapping("/sign-up/kakao")
    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = JwtTokenRes.class)))
    public JwtTokenRes signUp(@RequestParam String key, @RequestBody @Valid SignUpReq signUpReq) {
        if (signUpReq.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalEmailException();
        }

        return authService.signUp(key, signUpReq);
    }

    @GetMapping("/sign-up/random-nickname")
    @Operation(summary = "랜덤 닉네임 발급", description = "회원가입 시 1회 요청")
    public String getRandomNickname() {
        return authService.generateRandomNickname();
    }

    @PostMapping("/unregister")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 (+ 카카오 회원 탈퇴 처리)")
    public void unregister(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        unregisterService.unregister(reqMemberId);
    }

    /**
     * kakao logout 연동 x
     */
    @PostMapping("/auth/logout")
    public void logout(@RequestBody LogoutReq logoutReq) {
        authService.logout(logoutReq);
    }

    @PostMapping("/reissue/token")
    public AccessTokenRes reissueAccessToken(@RequestBody RefreshTokenReq refreshTokenReq) {
        String refreshToken = refreshTokenReq.getRefreshToken();
        return authService.reissueAccessToken(refreshToken);
    }

}

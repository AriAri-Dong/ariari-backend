package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.AccessTokenRes;
import com.ariari.ariari.commons.auth.dto.JwtTokenRes;
import com.ariari.ariari.commons.auth.dto.LogoutReq;
import com.ariari.ariari.commons.auth.dto.RefreshTokenReq;
import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth", description = "인증 관련 어노테이션")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoAuthManager kakaoAuthManager;

    /**
     * kakao login callback
     */
    @GetMapping("/login/kakao")
    @Operation(summary = "로그인", description = "카카오 로그인")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = JwtTokenRes.class)))
    public JwtTokenRes login(@RequestParam(name = "code") String code) {

        String token = kakaoAuthManager.getKakaoToken(code);
        Long kakaoId = kakaoAuthManager.getKakaoId(token);

        return authService.login(kakaoId);
    }

    @PostMapping("/unregister")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 (+ 카카오 회원 탈퇴 처리)")
    public void unregister(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        authService.unregister(reqMemberId);
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

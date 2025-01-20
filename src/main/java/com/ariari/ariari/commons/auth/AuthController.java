package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.JwtTokenRes;
import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    /**
     * kakao logout 연동 x
     */
    @PostMapping("/logout")
    public void logout(@RequestBody JwtTokenRes jwtTokenRes) {
        authService.logout(jwtTokenRes);
    }

}

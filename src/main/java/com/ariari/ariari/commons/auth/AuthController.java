package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.JwtTokenDto;
import com.ariari.ariari.commons.manager.KakaoAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoAuthManager kakaoAuthManager;

    /**
     * kakao login callback
     */
    @GetMapping("/login/kakao")
    public JwtTokenDto login(@RequestParam(name = "code") String code) {

        String token = kakaoAuthManager.getKakaoToken(code);
        Long kakaoId = kakaoAuthManager.getKakaoId(token);

        return authService.login(kakaoId);
    }

    /**
     * kakao logout 연동 x
     */
    @PostMapping("/logout")
    public void logout(@RequestBody JwtTokenDto jwtTokenDto) {
        authService.logout(jwtTokenDto);
    }

}

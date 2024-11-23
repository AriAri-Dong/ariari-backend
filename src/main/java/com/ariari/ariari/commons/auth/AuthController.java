package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.JwtTokenReq;
import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import lombok.RequiredArgsConstructor;
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
    public JwtTokenReq login(@RequestParam(name = "code") String code) {

        String token = kakaoAuthManager.getKakaoToken(code);
        Long kakaoId = kakaoAuthManager.getKakaoId(token);

        return authService.login(kakaoId);
    }

    /**
     * kakao logout 연동 x
     */
    @PostMapping("/logout")
    public void logout(@RequestBody JwtTokenReq jwtTokenReq) {
        authService.logout(jwtTokenReq);
    }

}

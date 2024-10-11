package com.ariari.ariari.commons.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthService {

    /**
     * kakao login callback
     */
    @GetMapping("/login/kakao")
    public JwtTokenDto login(@RequestParam(name = "code") String code) {
//        log.info("kakao auth controller start : {}", code);

        String token = kakaoAuthManager.getKakaoToken(code);
//        log.info("kakao token : {}", token);

        Long kakaoId = kakaoAuthManager.getKakaoId(token);
//        log.info("kakaoId : {}", kakaoId);

//        log.info("kakao auth controller end");
        return kakaoAuthService.login(kakaoId);
    }


}

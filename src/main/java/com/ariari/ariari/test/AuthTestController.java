package com.ariari.ariari.test;

import com.ariari.ariari.commons.auth.dto.JwtTokenRes;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.test.dto.TokenInfoRes;
import com.ariari.ariari.test.dto.TokenReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Tag(name = "로그인 테스트")
@RestController
@RequestMapping("/test/auth")
@RequiredArgsConstructor
public class AuthTestController {

    private final MemberRepository memberRepository;

    private final JwtManager jwtManager;

    @Operation(summary = "테스트용 토큰 획득 기능", description = "parameter(nickname) -> m1, m2, m3, ..., m6")
    @GetMapping("/token")
    public JwtTokenRes getTokenForTest(@RequestParam String nickname) {
        Member member = memberRepository.findByNickName(nickname).orElseThrow(NotFoundEntityException::new);
        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), JwtManager.TokenType.ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), JwtManager.TokenType.REFRESH_TOKEN);

        return JwtTokenRes.createRes(
                accessToken,
                refreshToken,
                false
        );
    }

    @Operation(summary = "토큰 정보 조회", description = "토큰 소유 회원의 id, 권한, 토큰 타입, 만료시간 정보를 반환합니다.")
    @PostMapping("/token-info")
    public TokenInfoRes getTokenInfo(@RequestBody TokenReq tokenReq) {
        String token = tokenReq.getToken();

        Long reqMemberId = jwtManager.getMemberId(token);
        List<? extends GrantedAuthority> authorities = jwtManager.getAuthorities(token).stream().toList();
        JwtManager.TokenType tokenType = jwtManager.getTokenType(token);
        LocalDateTime expirationDate = jwtManager.getExpirationDate(token);

        return TokenInfoRes.createRes(
                reqMemberId,
                authorities,
                tokenType,
                expirationDate
        );
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"})
    @GetMapping("/USER")
    public String userAuthTest() {
        return "successful";
    }

    @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
    @GetMapping("/MANAGER")
    public String ManagerAuthTest() {
        return "successful";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/ADMIN")
    public String adminAuthTest() {
        return "successful";
    }

}

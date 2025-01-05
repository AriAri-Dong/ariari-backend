package com.ariari.ariari.test;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Tag(name = "로그인 테스트")
@RestController
@RequestMapping("/test/auth")
@RequiredArgsConstructor
public class AuthTestController {

    private final MemberRepository memberRepository;

    private final JwtManager jwtManager;

    @Operation(summary = "테스트용 토큰 획득 기능", description = "parameter(nickname) -> m1, m2, m3, ..., m6")
    @GetMapping("/token")
    public String getTokenForTest(@RequestParam String nickname) {
        Member member = memberRepository.findByNickName(nickname).orElseThrow(NoSuchElementException::new);
        return jwtManager.generateToken(member.getAuthorities(), member.getId(), JwtManager.TokenType.ACCESS_TOKEN);
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

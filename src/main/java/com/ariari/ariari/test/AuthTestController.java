package com.ariari.ariari.test;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/test/auth")
@RequiredArgsConstructor
public class AuthTestController {

    private final MemberRepository memberRepository;

    private final JwtManager jwtManager;

    @GetMapping
    public Long test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return CustomUserDetails.getMemberId(userDetails, false);
    }

    @GetMapping("/token")
    public String getTokenForTest(@RequestParam(name = "userId") Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(NoSuchElementException::new);
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

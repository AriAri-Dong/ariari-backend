package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.JwtTokenDto;
import com.ariari.ariari.commons.manager.JwtControlManager;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RestController;

import static com.ariari.ariari.commons.manager.JwtManager.TokenType.*;

@RestController
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final JwtControlManager jwtControlManager;

    public JwtTokenDto login(Long kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);

        if (member == null) {
            member = signUp(kakaoId);
        }

        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), REFRESH_TOKEN);

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member signUp(Long kakaoId) {
        Member newMember = Member.createMember(kakaoId);
        newMember.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        memberRepository.save(newMember);
        return newMember;
    }

    public void logout(JwtTokenDto jwtTokenDto) {
        jwtControlManager.banToken(jwtTokenDto.getAccessToken());
        jwtControlManager.banToken(jwtTokenDto.getRefreshToken());
    }

}

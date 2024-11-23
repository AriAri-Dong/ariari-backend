package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.JwtTokenReq;
import com.ariari.ariari.commons.manager.JwtControlManager;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.ariari.ariari.commons.manager.JwtManager.TokenType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final JwtControlManager jwtControlManager;

    public JwtTokenReq login(Long kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);

        if (member == null) {
            member = signUp(kakaoId);
        }

        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), REFRESH_TOKEN);

        return JwtTokenReq.builder()
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

    public void logout(JwtTokenReq jwtTokenReq) {
        String accessToken = jwtTokenReq.getAccessToken();
        String refreshToken = jwtTokenReq.getRefreshToken();

        Date accessExpiration = jwtManager.getExpiration(accessToken);
        Date refreshExpiration = jwtManager.getExpiration(refreshToken);

        jwtControlManager.banToken(accessToken, accessExpiration);
        jwtControlManager.banToken(refreshToken, refreshExpiration);
    }

}

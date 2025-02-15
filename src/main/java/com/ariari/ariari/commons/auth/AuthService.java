package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.AccessTokenRes;
import com.ariari.ariari.commons.auth.dto.JwtTokenRes;
import com.ariari.ariari.commons.auth.nickname.NicknameCreator;
import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.JwtControlManager;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static com.ariari.ariari.commons.manager.JwtManager.TokenType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final JwtControlManager jwtControlManager;
    private final NicknameCreator nicknameCreator;
    private final KakaoAuthManager kakaoAuthManager;

    public JwtTokenRes login(Long kakaoId) {
        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);

        Member member;
        Boolean isFirstLogin;
        if (memberOptional.isEmpty()) {
            isFirstLogin = Boolean.TRUE;
            member = signUp(kakaoId);
        } else {
            isFirstLogin = Boolean.FALSE;
            member = memberOptional.get();
        }

        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), REFRESH_TOKEN);

        return JwtTokenRes.createRes(
                accessToken,
                refreshToken,
                isFirstLogin);
    }

    private Member signUp(Long kakaoId) {
        String nickname = nicknameCreator.createUniqueNickname();

        Member newMember = Member.createMember(kakaoId, nickname);
        newMember.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        memberRepository.save(newMember);
        return newMember;
    }

    public void unregister(Long reqMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        kakaoAuthManager.unregister(reqMember);
        memberRepository.delete(reqMember);
    }

    public void logout(JwtTokenRes jwtTokenRes) {
        String accessToken = jwtTokenRes.getAccessToken();
        String refreshToken = jwtTokenRes.getRefreshToken();

        Date accessExpiration = jwtManager.getExpiration(accessToken);
        Date refreshExpiration = jwtManager.getExpiration(refreshToken);

        jwtControlManager.banToken(accessToken, accessExpiration);
        jwtControlManager.banToken(refreshToken, refreshExpiration);
    }

    public AccessTokenRes reissueAccessToken(String refreshToken) {
        jwtManager.validateRefresh(refreshToken);

        Long reqMemberId = jwtManager.getMemberId(refreshToken);
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        String accessToken = jwtManager.generateToken(reqMember.getAuthorities(), reqMemberId, ACCESS_TOKEN);
        return AccessTokenRes.createRes(accessToken);
    }

}

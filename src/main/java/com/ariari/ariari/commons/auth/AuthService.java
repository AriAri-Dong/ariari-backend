package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.dto.AccessTokenRes;
import com.ariari.ariari.commons.auth.dto.JwtTokenRes;
import com.ariari.ariari.commons.auth.dto.LogoutReq;
import com.ariari.ariari.commons.auth.nickname.NicknameCreator;
import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import com.ariari.ariari.commons.auth.oauth.OAuthSignUpManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.JwtControlManager;
import com.ariari.ariari.commons.manager.JwtManager;
import com.ariari.ariari.domain.club.question.ClubQuestionService;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final ClubQuestionService clubQuestionService;
    private final OAuthSignUpManager oAuthSignUpManager;

    public JwtTokenRes login(String code) {

        // get kakao api token by kakao code
        String token = kakaoAuthManager.getKakaoToken(code);

        // get user info by kakao api token
        Long kakaoId = kakaoAuthManager.getKakaoId(token);

        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
        if (memberOptional.isEmpty()) {
            return JwtTokenRes.createRes(
                    null,
                    null,
                    oAuthSignUpManager.issueSignUpKey(token)
            );
        }

        Member member = memberOptional.get();
        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), REFRESH_TOKEN);

        // update last login date/time
        member.setLastLoginDateTime(LocalDateTime.now());

        return JwtTokenRes.createRes(
                accessToken,
                refreshToken,
                null);
    }

    public JwtTokenRes signUp(String key) {
        String token = oAuthSignUpManager.getToken(key);
        Long kakaoId = kakaoAuthManager.getKakaoId(token);

        Member member = signUp(kakaoId);

        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), REFRESH_TOKEN);

        member.setLastLoginDateTime(LocalDateTime.now());

        return JwtTokenRes.createRes(
                accessToken,
                refreshToken,
                null);
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
        clubQuestionService.changeStateByMember(reqMemberId);
        kakaoAuthManager.unregister(reqMember);
        memberRepository.delete(reqMember);
    }

    public void logout(LogoutReq logoutReq) {
        String accessToken = logoutReq.getAccessToken();
        String refreshToken = logoutReq.getRefreshToken();

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

package com.ariari.ariari.domain.member.member;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.dto.req.NicknameModifyReq;
import com.ariari.ariari.domain.member.member.dto.req.ProfileModifyReq;
import com.ariari.ariari.domain.member.member.dto.res.MemberDetailRes;
import com.ariari.ariari.domain.member.exceptions.ExistingNicknameException;
import com.ariari.ariari.domain.member.member.dto.res.MemberListRes;
import com.ariari.ariari.domain.member.member.exceptions.NoMemberSearchingAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;

    public MemberDetailRes findMyMemberDetail(Long reqMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        return MemberDetailRes.createRes(reqMember);
    }

    @Transactional
    public void modifyNickname(Long reqMemberId, NicknameModifyReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        if (memberRepository.existsByNickName(modifyReq.getNickname())) {
            throw new ExistingNicknameException();
        }

        modifyReq.modifyNickname(reqMember);
    }

    @Transactional
    public void modifyProfile(Long reqMemberId, ProfileModifyReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        modifyReq.modifyNickname(reqMember);
    }

    public MemberListRes searchMembers(Long reqMemberId, String nickname) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        if (!clubMemberRepository.existsMyManagerOrHigher(reqMember)) {
            throw new NoMemberSearchingAuthException();
        }

        List<Member> members = memberRepository.find20ByNickNameContains(nickname);
        return MemberListRes.createRes(members);
    }

}

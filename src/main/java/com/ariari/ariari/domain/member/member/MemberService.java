package com.ariari.ariari.domain.member.member;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.dto.req.NicknameModifyReq;
import com.ariari.ariari.domain.member.dto.req.ProfileModifyReq;
import com.ariari.ariari.domain.member.dto.res.MemberDetailRes;
import com.ariari.ariari.domain.member.exceptions.ExistingNicknameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

}

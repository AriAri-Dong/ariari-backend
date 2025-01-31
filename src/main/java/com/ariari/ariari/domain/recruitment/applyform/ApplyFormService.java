package com.ariari.ariari.domain.recruitment.applyform;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.applyform.dto.req.ApplyFormModifyReq;
import com.ariari.ariari.domain.recruitment.applyform.dto.res.ApplyFormRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplyFormRepository applyFormRepository;

    public ApplyFormRes findApplyForm(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        ApplyForm applyForm = applyFormRepository.findFirstByClubOrderByCreatedDateTimeDesc(club).orElse(null);
        return applyForm == null ? null : ApplyFormRes.fromEntity(applyForm);
    }

    @Transactional(readOnly = false)
    public void modifyApplyForm(Long memberId, Long clubId, ApplyFormModifyReq modifyReq) {
        Member reqMember = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        applyFormRepository.save(modifyReq.toEntity(club));
    }

}

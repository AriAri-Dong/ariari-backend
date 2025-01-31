package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInClubSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.req.MyAppliesSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyListService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplyRepository applyRepository;

    public ApplyListRes searchAppliesInClub(Long reqMemberId, Long clubId, AppliesInClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        Page<Apply> page = applyRepository.searchApplyByClub(club, condition, pageable);
        return ApplyListRes.fromPage(page);
    }

    public ApplyListRes findMyApplies(Long reqMemberId, Pageable pageable, MyAppliesSearchCondition searchType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Page<Apply> page = applyRepository.searchByMember(reqMember, searchType, pageable);
        return ApplyListRes.fromPage(page);
    }

}

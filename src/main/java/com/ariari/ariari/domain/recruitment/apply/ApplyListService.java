package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.exception.NoClubMemberException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInClubSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.req.MyAppliesSearchType;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyListRes;
import com.ariari.ariari.domain.recruitment.apply.exception.NoApplyAuthException;
import com.ariari.ariari.domain.recruitment.apply.exception.SearchAppliesInClubException;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyListService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplyRepository applyRepository;
    private final ApplyTempRepository applyTempRepository;

    public ApplyListRes searchAppliesInClub(Long reqMemberId, Long clubId, AppliesInClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoApplyAuthException::new);
        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubMemberException();
        }

        LocalDateTime start = condition.getStartDateTime();
        LocalDateTime end = condition.getEndDateTime();
        if ((start == null && end != null) || start != null && end == null) {
            throw new SearchAppliesInClubException();
        }

        Page<Apply> page = applyRepository.searchApplyByClub(club, condition, pageable);
        return ApplyListRes.fromPage(page);
    }

    // myApplies 조회와 myTempApplies 조회를 분리합니다.
    public ApplyListRes findMyApplies(Long reqMemberId, Pageable pageable, MyAppliesSearchType searchType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Page<Apply> page = applyRepository.searchByMember(reqMember, searchType, pageable);
        return ApplyListRes.fromPage(page);
    }

}

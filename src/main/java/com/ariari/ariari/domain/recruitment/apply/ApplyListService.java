package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInTeamSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.req.MyAppliesSearchType;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyListRes;
import com.ariari.ariari.domain.recruitment.apply.exception.NoApplyAuthException;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTempRepository;
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
    private final ApplyTempRepository applyTempRepository;

    public ApplyListRes searchAppliesInClub(Long reqMemberId, Long clubId, AppliesInTeamSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoApplyAuthException::new);
        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        Page<Apply> page = applyRepository.searchApplyByClub(club, condition, pageable);
        return ApplyListRes.fromPage(page);
    }

    /**
     * 세가지 케이스 : 임시데이터 / 임시 + 지원 / 지원서
     */
    public ApplyListRes findMyApplies(Long reqMemberId, Pageable pageable, MyAppliesSearchType searchType) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        if (searchType.equals(MyAppliesSearchType.TEMP)) {
            Page<ApplyTemp> page = applyTempRepository.findByMember(reqMember, pageable);
            return ApplyListRes.fromTempPage(page);
        } else if (searchType.equals(MyAppliesSearchType.IN_PROGRESS)) {
            Page<Apply> page = applyRepository.findByMember(reqMember, pageable);
            return ApplyListRes.fromPage(page);
        } else if (searchType.equals(MyAppliesSearchType.FINALIZED)) {
            Page<Apply> page = applyRepository.findFinalizedAppliesByMember(reqMember, pageable);
            return ApplyListRes.fromPage(page);
        } else {
            return null;

        }
    }

}

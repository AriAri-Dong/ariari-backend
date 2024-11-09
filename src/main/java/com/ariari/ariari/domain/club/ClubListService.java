package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.dto.ClubListRes;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubListService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    public ClubListRes findExternalList(Long memberId, ClubCategoryType clubCategoryType, Pageable pageable) {
        Page<Club> page = clubRepository.findExternalByClubCategoryType(clubCategoryType, pageable);
        return ClubListRes.fromPage(page);
    }

    public ClubListRes findInternalList(Long memberId, ClubCategoryType clubCategoryType, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        if (member.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<Club> page = clubRepository.findInternalByClubCategoryType(member.getSchool(), clubCategoryType, pageable);
        return ClubListRes.fromPage(page);
    }

    public ClubListRes findMyExternalList(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        Page<ClubMember> page = clubMemberRepository.findExternalByMember(member, pageable);
        return ClubListRes.fromClubMemberPage(page);
    }

    public ClubListRes findMyInternalList(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        if (member.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<ClubMember> page = clubMemberRepository.findInternalByMember(member, member.getSchool(), pageable);
        return ClubListRes.fromClubMemberPage(page);
    }

}

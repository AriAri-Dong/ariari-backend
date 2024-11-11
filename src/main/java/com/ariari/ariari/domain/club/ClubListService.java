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

    public ClubListRes findExternalList(Long reqMemberId, ClubCategoryType clubCategoryType, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElse(null);

        Page<Club> page = clubRepository.findExternalByClubCategoryType(clubCategoryType, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes findInternalList(Long reqMemberId, ClubCategoryType clubCategoryType, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<Club> page = clubRepository.findInternalByClubCategoryType(reqMember.getSchool(), clubCategoryType, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes findMyExternalList(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Page<ClubMember> page = clubMemberRepository.findExternalByMember(reqMember, pageable);
        return ClubListRes.fromClubMemberPage(page, reqMember);
    }

    public ClubListRes findMyInternalList(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<ClubMember> page = clubMemberRepository.findInternalByMember(reqMember, reqMember.getSchool(), pageable);
        return ClubListRes.fromClubMemberPage(page, reqMember);
    }

}

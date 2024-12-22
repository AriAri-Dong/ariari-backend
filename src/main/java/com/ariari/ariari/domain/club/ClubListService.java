package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.bookmark.ClubBookmarkRepository;
import com.ariari.ariari.domain.club.dto.res.ClubListRes;
import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubListService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubBookmarkRepository clubBookmarkRepository;

    public ClubListRes searchClubPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElse(null);
        School school = null;
        if (reqMember != null) {
            school = reqMember.getSchool();
        }

        Page<Club> page = clubRepository.searchClubPage(school, condition, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes searchExternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElse(null);

        Page<Club> page = clubRepository.searchExternalPage(condition, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes searchInternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<Club> page = clubRepository.searchInternalPage(reqMember.getSchool(), condition, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes findMyClubList(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Page<ClubMember> page = clubMemberRepository.findByMember(reqMember, pageable);
        return ClubListRes.fromClubMemberPage(page, reqMember);
    }

    public ClubListRes findMyBookmarkClubsList(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Page<ClubBookmark> page = clubBookmarkRepository.findByMember(reqMember, pageable);
        return ClubListRes.fromClubBookmarkPage(page, reqMember);
    }

    public ClubListRes findClubListByWord(Long reqMemberId, String query, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElse(null);
        School school = null;
        if (reqMember != null) {
            school = reqMember.getSchool();
        }

        Page<Club> page = clubRepository.findByNameContains(query, school, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes findExternalClubRankingList(Long reqMemberId, ClubCategoryType categoryType) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElse(null);

        List<Club> clubs = clubRepository.findExternalClubRankingList(categoryType);
        return ClubListRes.fromList(clubs, reqMember);
    }

    public ClubListRes findInternalClubRankingList(Long reqMemberId, ClubCategoryType categoryType) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);

        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        List<Club> clubs = clubRepository.findInternalClubRankingList(reqMember.getSchool(), categoryType);
        return ClubListRes.fromList(clubs, reqMember);
    }

}

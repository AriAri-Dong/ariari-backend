package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.ClubActivityRepository;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.club.club.dto.MyClubData;
import com.ariari.ariari.domain.club.club.dto.res.ClubListRes;
import com.ariari.ariari.domain.club.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.club.dto.res.MyClubListRes;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.club.event.ClubEventRepository;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.notice.ClubNoticeRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubListService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubActivityRepository clubActivityRepository;

    public ClubListRes searchClubPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }

        School school = null;
        if (reqMember != null) {
            school = reqMember.getSchool();
        }

        Page<ClubData> page = clubRepository.searchClubPage(school, condition, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes searchExternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }

        Page<ClubData> page = clubRepository.searchExternalPage(condition, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes searchInternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        GlobalValidator.hasSchoolAuth(reqMember);

        Page<ClubData> page = clubRepository.searchInternalPage(reqMember.getSchool(), condition, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public MyClubListRes findMyClubList(Long reqMemberId) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);

        List<ClubMember> clubMembers = clubMemberRepository.findByMember(reqMember);
        List<Club> clubs = clubMembers.stream().map(ClubMember::getClub).toList();

        List<MyClubData> myClubDataList = new ArrayList<>();
        for (Club club : clubs) {
            ClubNotice clubNotice = clubNoticeRepository.findFirstByClubOrderByCreatedDateTimeDesc(club).orElse(null);
            ClubActivity clubActivity = clubActivityRepository.findFirstByClubOrderByCreatedDateTimeDesc(club).orElse(null);
            myClubDataList.add(new MyClubData(club, clubNotice, clubActivity));
        }

        return MyClubListRes.createRes(myClubDataList);
    }

    public ClubListRes findMyAdminClubList(Long reqMemberId) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        List<ClubMember> clubMembers = clubMemberRepository.findByMember(reqMember);

        clubMembers = clubMembers.stream().filter(cm -> cm.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)).toList();
        return ClubListRes.fromClubMemberList(clubMembers);
    }

    public ClubListRes findMyBookmarkClubsList(Long reqMemberId, Boolean hasActiveRecruitment, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Page<ClubData> page = clubRepository.findMyBookmarkClubs(reqMember, hasActiveRecruitment, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes findClubListByWord(Long reqMemberId, String query, Pageable pageable) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }
        School school = null;
        if (reqMember != null) {
            school = reqMember.getSchool();
        }

        Page<ClubData> page = clubRepository.findByNameContains(query, school, pageable);
        return ClubListRes.fromPage(page, reqMember);
    }

    public ClubListRes findExternalClubRankingList(Long reqMemberId, ClubCategoryType categoryType) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }

        List<Club> clubs = clubRepository.findExternalClubRankingList(categoryType);
        return ClubListRes.fromList(clubs, reqMember);
    }

    public ClubListRes findInternalClubRankingList(Long reqMemberId, ClubCategoryType categoryType) {
        Member reqMember = memberRepository.findByIdWithClubBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);
        GlobalValidator.hasSchoolAuth(reqMember);

        List<Club> clubs = clubRepository.findInternalClubRankingList(reqMember.getSchool(), categoryType);
        return ClubListRes.fromList(clubs, reqMember);
    }

}

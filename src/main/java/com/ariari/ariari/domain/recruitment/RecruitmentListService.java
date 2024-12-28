package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentListRes;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 작성 중
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentListService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentListRes searchRecruitmentPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElse(null);
        
        School school = null;
        if (reqMember != null) {
            school = reqMember.getSchool();
        }

        Page<Recruitment> page = recruitmentRepository.searchRecruitmentPage(school, condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

    public RecruitmentListRes searchExternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElse(null);

        Page<Recruitment> page = recruitmentRepository.searchExternalPage(condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

    public RecruitmentListRes searchInternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NoSchoolAuthException::new);

        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<Recruitment> page = recruitmentRepository.searchInternalPage(reqMember.getSchool(), condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }
    
    public RecruitmentListRes findMyBookmarkRecruitmentList(Long reqMemberId) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);

        List<Recruitment> recruitments = reqMember.getRecruitmentBookmarks().stream().map(RecruitmentBookmark::getRecruitment).toList();
        return RecruitmentListRes.fromList(recruitments, reqMember);
    }

    public RecruitmentListRes findRecruitmentListInClub(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NoSchoolAuthException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        List<Recruitment> recruitments = recruitmentRepository.findByClub(club);
        return RecruitmentListRes.fromList(recruitments, reqMember);
    }

    public RecruitmentListRes findExternalRankingList(Long reqMemberId) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElse(null);

        List<Recruitment> recruitments = recruitmentRepository.findExternalRankingList();
        return RecruitmentListRes.fromList(recruitments, reqMember);
    }

    public RecruitmentListRes findInternalRankingList(Long reqMemberId) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NoSchoolAuthException::new);

        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        List<Recruitment> recruitments = recruitmentRepository.findInternalRankingList(reqMember.getSchool());
        return RecruitmentListRes.fromList(recruitments, reqMember);
    }

}

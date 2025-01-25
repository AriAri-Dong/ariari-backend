package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmarkRepository;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentListRes;
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
public class RecruitmentListService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentBookmarkRepository recruitmentBookmarkRepository;

    public RecruitmentListRes searchRecruitmentPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }
        
        School school = null;
        if (reqMember != null) {
            school = reqMember.getSchool();
        }

        Page<Recruitment> page = recruitmentRepository.searchRecruitmentPage(school, condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

    public RecruitmentListRes searchExternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }

        Page<Recruitment> page = recruitmentRepository.searchExternalPage(condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

    public RecruitmentListRes searchInternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NoSchoolAuthException::new);

        GlobalValidator.hasSchoolAuth(reqMember);

        Page<Recruitment> page = recruitmentRepository.searchInternalPage(reqMember.getSchool(), condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }
    
    public RecruitmentListRes findMyBookmarkRecruitmentList(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Page<RecruitmentBookmark> page = recruitmentBookmarkRepository.findByMember(reqMember, pageable);
        return RecruitmentListRes.fromBookmarkPage(page, reqMember);
    }

    public RecruitmentListRes findRecruitmentListInClub(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NoSchoolAuthException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());

        List<Recruitment> recruitments = recruitmentRepository.findByClub(club);
        return RecruitmentListRes.fromList(recruitments, reqMember);
    }

    public RecruitmentListRes findExternalRankingList(Long reqMemberId) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }

        List<Recruitment> recruitments = recruitmentRepository.findExternalRankingList();
        return RecruitmentListRes.fromList(recruitments, reqMember);
    }

    public RecruitmentListRes findInternalRankingList(Long reqMemberId) {
        Member reqMember = memberRepository.findByIdWithRecruitmentBookmarks(reqMemberId).orElseThrow(NoSchoolAuthException::new);

        GlobalValidator.hasSchoolAuth(reqMember);

        List<Recruitment> recruitments = recruitmentRepository.findInternalRankingList(reqMember.getSchool());
        return RecruitmentListRes.fromList(recruitments, reqMember);
    }

}

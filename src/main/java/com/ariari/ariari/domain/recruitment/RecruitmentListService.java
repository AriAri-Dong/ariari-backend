package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentListRes;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 작성 중
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentListService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentListRes searchRecruitmentPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElse(null);
        
        School school = null;
        if (reqMember != null) {
            school = reqMember.getSchool();
        }

        Page<Recruitment> page = recruitmentRepository.searchRecruitmentPage(school, condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

    public RecruitmentListRes searchExternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElse(null);

        Page<Recruitment> page = recruitmentRepository.searchExternalPage(condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

    public RecruitmentListRes searchInternalPage(Long reqMemberId, ClubSearchCondition condition, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<Recruitment> page = recruitmentRepository.searchInternalPage(reqMember.getSchool(), condition, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }
    
    public RecruitmentListRes findMyBookmarkRecruitmentList(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        return null;
    }

}

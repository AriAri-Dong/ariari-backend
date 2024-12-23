package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.domain.recruitment.dto.res.RecruitmentListRes;
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

    public RecruitmentListRes findExternalList(Long memberId, ClubCategoryType clubCategoryType, Pageable pageable) {
        Member reqMember = memberRepository.findById(memberId).orElse(null);

        Page<Recruitment> page = recruitmentRepository.findExternalByClubCategoryType(clubCategoryType, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

    public RecruitmentListRes findInternalList(Long memberId, ClubCategoryType clubCategoryType, Pageable pageable) {
        Member reqMember = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        if (reqMember.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<Recruitment> page = recruitmentRepository.findInternalByClubCategoryType(reqMember.getSchool(), clubCategoryType, pageable);
        return RecruitmentListRes.fromPage(page, reqMember);
    }

}

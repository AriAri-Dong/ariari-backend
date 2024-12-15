package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentListRes;
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
        Page<Recruitment> page = recruitmentRepository.findExternalByClubCategoryType(clubCategoryType, pageable);
        return RecruitmentListRes.fromPage(page);
    }

    public RecruitmentListRes findInternalList(Long memberId, ClubCategoryType clubCategoryType, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        if (member.getSchool() == null) {
            throw new NoSchoolAuthException();
        }

        Page<Recruitment> page = recruitmentRepository.findInternalByClubCategoryType(member.getSchool(), clubCategoryType, pageable);
        return RecruitmentListRes.fromPage(page);
    }

}

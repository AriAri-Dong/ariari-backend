package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.commons.exception.exceptions.NotAuthenticatedException;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

/**
 * 작성 중
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;

    public List<RecruitmentData> findRecruitmentRankingList(Long memberId, ClubAffiliationType clubAffiliationType) {

        List<Recruitment> recruitments;
        if (clubAffiliationType.equals(ClubAffiliationType.EXTERNAL)) {
            recruitments = recruitmentRepository.findRanking();
        } else {
            if (memberId == null) {
                throw new NotAuthenticatedException();
            }

            Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
            if (member.getSchool() == null) {
                throw new NoSchoolAuthException();
            }

            recruitments = recruitmentRepository.findRankingBySchool(member.getSchool());
        }

        return RecruitmentData.fromEntities(recruitments);
    }

    public List<RecruitmentData> findRecruitmentList(Long memberId, ClubAffiliationType clubAffiliationType, ClubCategoryType clubCategoryType, Pageable pageable) {

        Page<Recruitment> recruitments;
        if (clubAffiliationType.equals(ClubAffiliationType.EXTERNAL)) {
            if (clubCategoryType != null) {
                recruitments = recruitmentRepository.findActivatedAll(pageable);
            } else {
                recruitments = recruitmentRepository.findByClub_ClubCategoryType(clubCategoryType, pageable);
            }
        } else {
            if (clubCategoryType != null) {

            }
        }

        return null;
    }

}

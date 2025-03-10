package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.bookmark.exception.AlreadyExistsRecruitmentBookmarkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentBookmarkService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentBookmarkRepository recruitmentBookmarkRepository;

    public void saveBookmark(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.eqSchoolAuth(reqMember, recruitment.getClub().getSchool());

        if (recruitmentBookmarkRepository.existsByMemberAndRecruitment(reqMember, recruitment)) {
            throw new AlreadyExistsRecruitmentBookmarkException();
        }

        RecruitmentBookmark recruitmentBookmark = new RecruitmentBookmark(recruitment, reqMember);
        recruitmentBookmarkRepository.save(recruitmentBookmark);
    }

    public void removeBookmark(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);

        RecruitmentBookmark recruitmentBookmark = recruitmentBookmarkRepository.findByMemberAndRecruitment(reqMember, recruitment).orElseThrow(NotFoundEntityException::new);
        recruitmentBookmarkRepository.delete(recruitmentBookmark);
    }

}

package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.bookmark.exception.AlreadyExistsRecruitmentBookmarkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        Boolean exists = recruitmentBookmarkRepository.existsByMemberAndRecruitment(reqMember, recruitment);
        if (exists) {
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

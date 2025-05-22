package com.ariari.ariari.commons.auth;

import com.ariari.ariari.commons.auth.oauth.KakaoAuthManager;
import com.ariari.ariari.commons.entity.report.ReportRepository;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.activity.ClubActivityRepository;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityCommentRepository;
import com.ariari.ariari.domain.club.notice.ClubNoticeRepository;
import com.ariari.ariari.domain.club.passreview.repository.PassReviewRepository;
import com.ariari.ariari.domain.club.question.ClubQuestionRepository;
import com.ariari.ariari.domain.club.review.repository.ClubReviewRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnregisterService {

    private final MemberRepository memberRepository;
    private final PassReviewRepository passReviewRepository;
    private final ClubReviewRepository clubReviewRepository;
    private final ReportRepository reportRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubActivityRepository clubActivityRepository;
    private final ClubActivityCommentRepository clubActivityCommentRepository;

    private final KakaoAuthManager kakaoAuthManager;

    public void unregister(Long reqMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        // member_id -> null
        passReviewRepository.updateMemberNull(reqMember);
        clubReviewRepository.updateMemberNull(reqMember);
        reportRepository.updateMemberNull(reqMember);
        clubNoticeRepository.updateMemberNull(reqMember);
        clubQuestionRepository.updateMemberNull(reqMember);
        clubActivityRepository.updateMemberNull(reqMember);
        clubActivityCommentRepository.updateMemberNull(reqMember);

        kakaoAuthManager.unregister(reqMember);
        memberRepository.delete(reqMember);
    }

}

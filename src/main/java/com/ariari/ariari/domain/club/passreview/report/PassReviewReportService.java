package com.ariari.ariari.domain.club.passreview.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.repository.PassReviewRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassReviewReportService {

    private final PassReviewRepository passReviewRepository;
    private final PassReviewReportRepository passReviewReportRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void reportPassReview(Long reporterId, ReportReq reportPassReviewReq) {

        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportPassReviewReq.getReportedEntityId();
        PassReview reportedPassReview = passReviewRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if(passReviewReportRepository.existsByReporterAndReportedPassReview(reporterMember, reportedPassReview)){
            throw new ReportExistsException();
        };

        // 합격 후기 신고 생성
        PassReviewReport report = PassReviewReport.builder()
                .reportedPassReview(reportedPassReview)
                .reportType(reportPassReviewReq.getReportType())
                .reporter(reporterMember)
                .body(reportPassReviewReq.getBody())
                .build();
        // 합격 후기 신고 저장
        passReviewReportRepository.save(report);
    }
}

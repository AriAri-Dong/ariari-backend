package com.ariari.ariari.domain.club.review.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.club.review.repository.ClubReviewRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubReviewReportService {

    private final ClubReviewRepository clubReviewRepository;
    private final ClubReviewReportRepository clubReviewReportRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void reportClubReview(Long reporterId, ReportReq reportClubReviewReq) {

        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportClubReviewReq.getReportedEntityId();
        ClubReview reportedClubReview = clubReviewRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if(clubReviewReportRepository.existsByReporterAndReportedClubReview(reporterMember, reportedClubReview)){
            throw new ReportExistsException();
        };

        // 동아리 리뷰 신고 생성
        ClubReviewReport report = ClubReviewReport.builder()
                .reportedClubReview(reportedClubReview)
                .reportType(reportClubReviewReq.getReportType())
                .reporter(reporterMember)
                .body(reportClubReviewReq.getBody())
                .build();
        // 동아리 리뷰 신고 저장
        clubReviewReportRepository.save(report);
    }



}

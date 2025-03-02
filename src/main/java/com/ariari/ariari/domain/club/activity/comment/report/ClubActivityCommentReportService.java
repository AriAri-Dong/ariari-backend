package com.ariari.ariari.domain.club.activity.comment.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityCommentRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubActivityCommentReportService {

    private final MemberRepository memberRepository;
    private final ClubActivityCommentRepository clubActivityCommentRepository;
    private final ClubActivityCommentReportRepository clubActivityCommentReportRepository;

    @Transactional
    public void reportClubActivityComment(Long reporterId, ReportReq reportClubActivityCommentReq) {

        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportClubActivityCommentReq.getReportedEntityId();
        ClubActivityComment reportedClubActivityComment = clubActivityCommentRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if(clubActivityCommentReportRepository.existsByReporterAndReportedClubActivityComment(reporterMember, reportedClubActivityComment)){
            throw new ReportExistsException();
        };

        // 회원 신고 생성
        ClubActivityCommentReport report = ClubActivityCommentReport.builder()
                .reporter(reporterMember)
                .reportedClubActivityComment(reportedClubActivityComment)
                .reportType(reportClubActivityCommentReq.getReportType())
                .body(reportClubActivityCommentReq.getBody())
                .build();
        // 동아리 활동
        clubActivityCommentReportRepository.save(report);
    }
}

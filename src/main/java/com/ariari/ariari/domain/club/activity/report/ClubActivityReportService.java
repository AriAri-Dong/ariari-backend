package com.ariari.ariari.domain.club.activity.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.ClubActivityRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubActivityReportService {

    private final MemberRepository memberRepository;
    private final ClubActivityReportRepository clubActivityReportRepository;
    private final ClubActivityRepository clubActivityRepository;

    @Transactional
    public void reportClubActivity(Long reporterId, ReportReq reportClubActivityReq) {

        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportClubActivityReq.getReportedEntityId();
        ClubActivity reportedClubActivity = clubActivityRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if(clubActivityReportRepository.existsByReporterAndReportedClubActivity(reporterMember, reportedClubActivity)){
            throw new ReportExistsException();
        };

        // 동아리 활동 신고 생성
        ClubActivityReport report = ClubActivityReport.builder()
                .reporter(reporterMember)
                .reportedClubActivity(reportedClubActivity)
                .reportType(reportClubActivityReq.getReportType())
                .body(reportClubActivityReq.getBody())
                .build();
        // 동아리 활동 신고 저장
        clubActivityReportRepository.save(report);
    }
}

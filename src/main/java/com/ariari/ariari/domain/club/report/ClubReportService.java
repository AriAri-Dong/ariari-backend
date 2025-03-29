package com.ariari.ariari.domain.club.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubReportService {

    private final ClubRepository clubRepository;
    private final ClubReportRepository clubReportRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void reportClub(Long reporterId, ReportReq reportClubReq) {
        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportClubReq.getReportedEntityId();
        Club reportedClub = clubRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if (clubReportRepository.existsByReporterAndReportedClub(reporterMember, reportedClub)){
            throw new ReportExistsException();
        };

        // 동아리 신고 생성
        ClubReport report = ClubReport.builder()
                .reportedClub(reportedClub)
                .reportType(reportClubReq.getReportType())
                .reporter(reporterMember)
                .body(reportClubReq.getBody())
                .build();
        // 동아리 신고 저장
        clubReportRepository.save(report);
    }
}

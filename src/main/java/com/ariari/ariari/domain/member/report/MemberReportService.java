package com.ariari.ariari.domain.member.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberReportService {

    private final MemberReportRepository memberReportRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void reportMember(Long reporterId, ReportReq reportMemberReq) {
        // 신고자 찾기
       Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
       // 신고 대상 찾기
        long reportedId = reportMemberReq.getReportedEntityId();
       Member reportedMember = memberRepository.findByIdWithAuthorities(reportedId).orElseThrow(NotFoundEntityException::new);
        // 동일한 신고가 있는지 체크
        if(memberReportRepository.existsByReporterAndReportedMember(reporterMember, reportedMember)){
            throw new ReportExistsException();
        };

       // 회원 신고 생성
        MemberReport report = MemberReport.builder()
               .reporter(reporterMember)
               .reportedMember(reportedMember)
               .reportType(reportMemberReq.getReportType())
               .body(reportMemberReq.getBody())
               .build();

       // 회원 신고 저장
        memberReportRepository.save(report);
    }

}

package com.ariari.ariari.domain.member.report;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.dto.req.ReportMemberReq;
import com.ariari.ariari.domain.member.exceptions.ReportExistsException;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberReportService {

    private final MemberReportRepository memberReportRepository;
    private final MemberRepository memberRepository;
    private final ReportCheck reportCheck;

    @Transactional
    public ResponseEntity<Void> reportMember(Long reporterId, ReportMemberReq reportMemberReq) {
        // 신고자 찾기
       Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
       // 신고 대상 찾기
       Member reportedMember = memberRepository.findByIdWithAuthorities(reportMemberReq.getReportedId()).orElseThrow(NotFoundEntityException::new);
       // 동일한 신고가 있는지 체크
        if(reportCheck.checkReport(reporterMember, reportedMember)){
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
        // 성공 응답 반환
        return ResponseEntity.ok().build();
    }

}

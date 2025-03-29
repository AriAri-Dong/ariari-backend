package com.ariari.ariari.domain.recruitment.apply.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyReportService {

    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;
    private final ApplyReportRepository applyReportRepository;

    @Transactional
    public void reportApply(Long reporterId, ReportReq reportApplyReq) {

        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportApplyReq.getReportedEntityId();
        Apply reportedApply = applyRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if(applyReportRepository.existsByReporterAndReportedApply(reporterMember, reportedApply)){
            throw new ReportExistsException();
        };

        // 지원 신고 생성
        ApplyReport report = ApplyReport.builder()
                .reporter(reporterMember)
                .reportedApply(reportedApply)
                .reportType(reportApplyReq.getReportType())
                .body(reportApplyReq.getBody())
                .build();

        // 지원 신고 저장
        applyReportRepository.save(report);
    }
}

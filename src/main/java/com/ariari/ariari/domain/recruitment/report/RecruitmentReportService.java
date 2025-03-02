package com.ariari.ariari.domain.recruitment.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentReportService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentReportRepository recruitmentReportRepository;

    @Transactional
    public void reportRecruitment(Long reporterId, ReportReq reportRecruitmentReq) {

        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportRecruitmentReq.getReportedEntityId();
        Recruitment reportedRecruitment= recruitmentRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if(recruitmentReportRepository.existsByReporterAndReportedRecruitment(reporterMember, reportedRecruitment)){
            throw new ReportExistsException();
        };
        // 모집 신고 생성
        RecruitmentReport report = RecruitmentReport.builder()
                .reporter(reporterMember)
                .reportedRecruitment(reportedRecruitment)
                .reportType(reportRecruitmentReq.getReportType())
                .body(reportRecruitmentReq.getBody())
                .build();

        // 모집 신고 저장
        recruitmentReportRepository.save(report);
    }
}

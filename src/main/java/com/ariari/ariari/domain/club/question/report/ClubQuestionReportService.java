package com.ariari.ariari.domain.club.question.report;

import com.ariari.ariari.commons.entity.report.dto.ReportReq;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.exception.exceptions.ReportExistsException;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.ClubQuestionRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubQuestionReportService {

    private final MemberRepository memberRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubQuestionReportRepository clubQuestionReportRepository;
    @Transactional
    public void reportClubQuestion(Long reporterId, ReportReq reportClubQuestionReq) {

        // 신고자 찾기
        Member reporterMember = memberRepository.findByIdWithAuthorities(reporterId).orElseThrow(NotFoundEntityException::new);
        // 신고 대상 찾기
        long reportedId = reportClubQuestionReq.getReportedEntityId();
        ClubQuestion reportedClubQuestion = clubQuestionRepository.findById(reportedId).orElseThrow(NotFoundEntityException::new);

        if(clubQuestionReportRepository.existsByReporterAndReportedClubQuestion(reporterMember, reportedClubQuestion)){
            throw new ReportExistsException();
        };

        // 동아리 질문 신고 생성
        ClubQuestionReport report = ClubQuestionReport.builder()
                .reporter(reporterMember)
                .reportedClubQuestion(reportedClubQuestion)
                .reportType(reportClubQuestionReq.getReportType())
                .body(reportClubQuestionReq.getBody())
                .build();

        // 동아리 질문 신고 생성
        clubQuestionReportRepository.save(report);
    }
}

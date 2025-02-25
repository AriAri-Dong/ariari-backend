package com.ariari.ariari.domain.member.report;

import com.ariari.ariari.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReportCheck {

    private final MemberReportRepository memberReportRepository;

    @Transactional(readOnly = true)
    public <T> boolean checkReport(Member reporterMember, T reportedType) {
        if (reportedType instanceof Member) {
            return memberReportRepository.existsByReportedMemberAndReporter(MemberReport.class, reporterMember, (Member) reportedType);
        }
        return false;
    }

}

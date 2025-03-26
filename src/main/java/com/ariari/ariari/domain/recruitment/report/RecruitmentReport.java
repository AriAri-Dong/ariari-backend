package com.ariari.ariari.domain.recruitment.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecruitmentReport extends Report {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_recruitment_id")
    private Recruitment reportedRecruitment;

    @Builder
    public RecruitmentReport(ReportType reportType, String body, Member reporter, Recruitment reportedRecruitment){
        super(reportType, body, reporter);
        this.reportedRecruitment = reportedRecruitment;
    }

}

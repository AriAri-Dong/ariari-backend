package com.ariari.ariari.domain.recruitment.report;

import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class RecruitmentReport {

    @Id @CustomPkGenerate
    @Column(name = "recruitment_report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ReportType reportType;

    @Column(length = 500)
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_member_id")
    private Member reportingMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_recruitment_id")
    private Recruitment reportedRecruitment;

}

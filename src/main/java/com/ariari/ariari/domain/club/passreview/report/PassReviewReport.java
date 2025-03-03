package com.ariari.ariari.domain.club.passreview.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.Apply;
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
public class PassReviewReport extends Report {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_pass_review_id")
    private PassReview reportedPassReview;

    @Builder
    public PassReviewReport(ReportType reportType, String body, Member reporter, PassReview reportedPassReview){
        super(reportType, body ,reporter);
        this.reportedPassReview = reportedPassReview;
    }

}

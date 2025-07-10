package com.ariari.ariari.domain.club.passreview.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.entity.report.enums.LocationType;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PassReviewReport extends Report {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_pass_review_id")
    private PassReview reportedPassReview;

    @Builder
    public PassReviewReport(ReportType reportType, String body, Member reporter, PassReview reportedPassReview, String locationUrl, LocationType locationType){
        super(reportType, body ,reporter, locationUrl, locationType);
        this.reportedPassReview = reportedPassReview;
    }

}

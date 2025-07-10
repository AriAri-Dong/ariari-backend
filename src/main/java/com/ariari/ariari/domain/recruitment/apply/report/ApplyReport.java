package com.ariari.ariari.domain.recruitment.apply.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.entity.report.enums.LocationType;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.Apply;
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
public class ApplyReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_apply_id")
    private Apply reportedApply;

    @Builder
    public ApplyReport(ReportType reportType, String body, Member reporter, Apply reportedApply, String locationUrl, LocationType locationType){
        super(reportType, body, reporter, locationUrl, locationType);
        this.reportedApply = reportedApply;
    }

}

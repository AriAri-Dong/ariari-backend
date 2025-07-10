package com.ariari.ariari.domain.member.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.entity.report.enums.LocationType;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MemberReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id")
    private Member reportedMember;


    @Builder
    public MemberReport(ReportType reportType, String body, Member reporter, Member reportedMember, String locationUrl, LocationType locationType){
        super(reportType, body, reporter, locationUrl, locationType);
        this.reportedMember = reportedMember;
    }

}

package com.ariari.ariari.domain.club.activity.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.entity.report.enums.LocationType;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.domain.club.activity.ClubActivity;
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
public class ClubActivityReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_club_activity_id")
    private ClubActivity reportedClubActivity;

    @Builder
    public ClubActivityReport(ReportType reportType , String body, Member reporter, ClubActivity reportedClubActivity, String locationUrl, LocationType locationType) {
        super(reportType, body, reporter, locationUrl, locationType);
        this.reportedClubActivity = reportedClubActivity;
    }

}

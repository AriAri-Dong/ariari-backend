package com.ariari.ariari.domain.club.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
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
public class ClubReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_clud_id")
    private Club reportedClub;

    @Builder
    public ClubReport(ReportType reportType, String body, Member reporter, Club reportedClub){
        super(reportType, body, reporter);
        this.reportedClub = reportedClub;
    }

}

package com.ariari.ariari.commons.entity.report;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.entity.report.enums.LocationType;
import com.ariari.ariari.commons.entity.report.enums.ReportStatusType;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.exception.exceptions.InvalidReportException;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;



@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SQLDelete(sql = "UPDATE report SET deleted_date_time= CURRENT_TIMESTAMP WHERE report_id= ?")
@SQLRestriction("deleted_date_time IS NULL")
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Report extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(length = 500)
    private String body;

    private String locationUrl;

    @Column(length = 500)
    private String resolveBody;

    private LocalDateTime resolvedDate;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Enumerated(EnumType.STRING)
    private ReportStatusType reportStatusType = ReportStatusType.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Member reporter;


    protected Report(ReportType reportType, String body, Member reporter, String locationUrl, LocationType  locationType) {
        if (reporter == null || reportType == null){
            throw new InvalidReportException();
        }
        this.reportType = reportType;
        this.body = body;
        this.reporter = reporter;
        this.locationUrl = locationUrl;
        this.locationType = locationType;
    }

    public void resolve(String resolveBody, LocalDateTime resolvedDate) {
        this.resolveBody = resolveBody;
        this.resolvedDate = resolvedDate;
        this.reportStatusType = ReportStatusType.RESOLVED;
    }

}

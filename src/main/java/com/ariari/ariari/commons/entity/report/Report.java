package com.ariari.ariari.commons.entity.report;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.exceptions.InvalidReportException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SQLDelete(sql = "UPDATE report SET deleted_date_time= CURRENT_TIMESTAMP WHERE report_id= ?")
@Table(
        indexes = @Index(name = "idx_dtype", columnList = "dtype")  // dtype 컬럼에 인덱스를 추가
)
@SQLRestriction("deleted_date_time  IS NULL")
@Getter
public abstract class Report extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(length = 500)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id")
    private Member reportedMember;

    protected Report(ReportType reportType, String body, Member reporter, Member reportedMember) {
        if (reporter == null || reportedMember == null || reportType == null){
            throw new InvalidReportException();
        }
        this.reportType = reportType;
        this.body = body;
        this.reporter = reporter;
        this.reportedMember = reportedMember;
    }
}

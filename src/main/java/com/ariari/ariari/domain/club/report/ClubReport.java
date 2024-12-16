package com.ariari.ariari.domain.club.report;

import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubReport {

    @Id @CustomPkGenerate
    @Column(name = "club_report_id")
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
    @JoinColumn(name = "reported_club_id")
    private Club reportedClub;

}

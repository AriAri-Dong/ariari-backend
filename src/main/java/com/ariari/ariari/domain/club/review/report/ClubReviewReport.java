package com.ariari.ariari.domain.club.review.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.review.ClubReview;
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
public class ClubReviewReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_club_review_id")
    private ClubReview reportedClubReview;

    @Builder
    public ClubReviewReport(ReportType reportType, String body, Member reporter, ClubReview reportedClubReview){
        super(reportType, body, reporter);
        this.reportedClubReview = reportedClubReview;
    }

}

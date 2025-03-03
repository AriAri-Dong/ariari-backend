package com.ariari.ariari.domain.club.question.report;


import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.question.ClubQuestion;
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
public class ClubQuestionReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_club_question_id")
    private ClubQuestion reportedClubQuestion;

    @Builder
    public ClubQuestionReport(ReportType reportType, String body, Member reporter, ClubQuestion reportedClubQuestion){
        super(reportType, body, reporter);
        this.reportedClubQuestion = reportedClubQuestion;
    }
}

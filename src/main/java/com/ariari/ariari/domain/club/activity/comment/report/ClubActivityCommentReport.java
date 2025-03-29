package com.ariari.ariari.domain.club.activity.comment.report;

import com.ariari.ariari.commons.entity.report.Report;
import com.ariari.ariari.commons.enums.ReportType;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubActivityCommentReport extends Report{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_club_activity_comment_id")
    private ClubActivityComment reportedClubActivityComment;

    @Builder
    public ClubActivityCommentReport(ReportType reportType , String body, Member reporter, ClubActivityComment reportedClubActivityComment){
        super(reportType, body, reporter);
        this.reportedClubActivityComment = reportedClubActivityComment;
    }

}

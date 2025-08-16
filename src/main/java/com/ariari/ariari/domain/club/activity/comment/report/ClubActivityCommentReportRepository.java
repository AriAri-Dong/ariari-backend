package com.ariari.ariari.domain.club.activity.comment.report;

import com.ariari.ariari.commons.entity.ClubActivityComment;
import com.ariari.ariari.commons.entity.ClubActivityCommentReport;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubActivityCommentReportRepository extends JpaRepository<ClubActivityCommentReport, Long> {

    boolean existsByReporterAndReportedClubActivityComment(Member reporter, ClubActivityComment reportedClubActivityComment);
}

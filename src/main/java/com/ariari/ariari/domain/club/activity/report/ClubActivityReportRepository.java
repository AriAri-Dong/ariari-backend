package com.ariari.ariari.domain.club.activity.report;

import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubActivityReportRepository extends JpaRepository<ClubActivityReport, Long> {
    boolean existsByReporterAndReportedClubActivity(Member reporter, ClubActivity reportedClubActivity);
}

package com.ariari.ariari.domain.club.report;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubReportRepository extends JpaRepository<ClubReport, Long> {

    boolean existsByReporterAndReportedClub(Member reporter, Club reportedClub);
}

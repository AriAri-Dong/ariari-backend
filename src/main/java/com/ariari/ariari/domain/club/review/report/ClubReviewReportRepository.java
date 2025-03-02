package com.ariari.ariari.domain.club.review.report;

import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubReviewReportRepository extends JpaRepository<ClubReviewReport, Long> {

    boolean existsByReporterAndReportedClubReview(Member reporter, ClubReview reportedClubReview);
}

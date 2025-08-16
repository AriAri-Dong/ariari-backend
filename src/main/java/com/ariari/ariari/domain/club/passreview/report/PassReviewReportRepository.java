package com.ariari.ariari.domain.club.passreview.report;

import com.ariari.ariari.commons.entity.PassReview;
import com.ariari.ariari.commons.entity.PassReviewReport;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassReviewReportRepository extends JpaRepository<PassReviewReport, Long> {

    boolean existsByReporterAndReportedPassReview(Member reporter, PassReview reportedPassReview);
}

package com.ariari.ariari.domain.recruitment.apply.report;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyReportRepository extends JpaRepository<ApplyReport, Long> {

    boolean existsByReporterAndReportedApply(Member reporter, Apply reportedApply);
}

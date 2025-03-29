package com.ariari.ariari.domain.club.question.report;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubQuestionReportRepository extends JpaRepository<ClubQuestionReport, Long> {

    boolean existsByReporterAndReportedClubQuestion(Member reporter, ClubQuestion reportedClubQuestion);
}

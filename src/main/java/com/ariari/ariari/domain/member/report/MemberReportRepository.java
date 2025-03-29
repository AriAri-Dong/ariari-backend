package com.ariari.ariari.domain.member.report;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberReportRepository extends JpaRepository<MemberReport , Long> {


    boolean existsByReporterAndReportedMember(Member reporter,Member reportedMember );

}

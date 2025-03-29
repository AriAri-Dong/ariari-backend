package com.ariari.ariari.commons.entity.report;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{

}

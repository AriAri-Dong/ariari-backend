package com.ariari.ariari.domain.member.report;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberReportRepository extends JpaRepository<MemberReport , Long> {

    @Query("select case when count(r) > 0 then true else false end " +
            "from Report r where TYPE(r) = :dtype and r.reportedMember = :reportedMember and r.reporter = :reporter")
    boolean existsByReportedMemberAndReporter(@Param ("dtype") Class<?> dtype,@Param("reporter")Member reporter,
                                              @Param("reportedMember") Member reportedMember);
}

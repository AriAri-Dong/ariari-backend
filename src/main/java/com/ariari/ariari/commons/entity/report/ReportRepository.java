package com.ariari.ariari.commons.entity.report;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{

    @Modifying(clearAutomatically = true)
    @Query("update Report r set r.reporter= null where r.reporter= :member")
    void updateMemberNull(Member member);

}

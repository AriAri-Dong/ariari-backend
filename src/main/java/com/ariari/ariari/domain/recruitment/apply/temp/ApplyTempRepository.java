package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplyTempRepository extends JpaRepository<ApplyTemp, Long>, ApplyTempRepositoryCustom {

    Page<ApplyTemp> searchByMember(Member reqMember, Pageable pageable);

    @Query("select a from ApplyTemp a " +
            "join fetch a.recruitment r " +
            "where r.endDateTime between :startDate and :endDate")
    List<ApplyTemp> findAllByWithinRecruitment(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

}

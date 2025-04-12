package com.ariari.ariari.domain.recruitment.recruitment;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>, RecruitmentRepositoryCustom {

    List<Recruitment> findByClub(Club club);


    @Query("select r from Recruitment r " +
            "join fetch r.club " +
            "where (r.endDateTime between :startDate and :endDate) " +
            "or (r.endDateTime between :startDate2 and :endDate2)")
    List<Recruitment> findAllByWithinRecruitment(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate,
                                                 @Param("startDate2") LocalDateTime startDate2,
                                                 @Param("endDate2") LocalDateTime endDate2);



}

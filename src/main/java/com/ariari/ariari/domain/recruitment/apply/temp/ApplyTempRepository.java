package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ApplyTempRepository extends JpaRepository<ApplyTemp, Long>, ApplyTempRepositoryCustom {

    @EntityGraph(attributePaths = {"recruitment", "recruitment.club"})
    Page<ApplyTemp> searchByMember(@Param("member") Member member, Pageable pageable);

    @Query("select a from ApplyTemp a " +
            "join fetch a.member m "+
            "join fetch a.recruitment r " +
            "where r.endDateTime between :startDate and :endDate")
    List<ApplyTemp> findAllByWithinRecruitment(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);
    @Query("select a from ApplyTemp a " +
            "join fetch a.member m "+
            "join fetch a.recruitment r " +
            "where a.recruitment = :recruitment")
    List<ApplyTemp> findAllByRecruitment(@Param("recruitment") Recruitment recruitment);

    Optional<ApplyTemp> findFirstByMemberAndRecruitmentOrderByCreatedDateTimeDesc(Member member, Recruitment recruitment);

    @Modifying(clearAutomatically = true)
    @Query("update ApplyTemp at set at.member= null where at.member= :member")
    void updateMemberNull(Member member);

}

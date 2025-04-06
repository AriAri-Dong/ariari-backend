package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long>, ApplyRepositoryCustom {

    @Query("select a from Apply a join fetch a.recruitment r join fetch r.club where a.id in :applyIds")
    List<Apply> findAllByIdsWithClub(List<Long> applyIds);

    Optional<Apply> findByMemberAndRecruitment(Member member, Recruitment recruitment);

    @Query("select a from Apply a where a.applyStatusType = :status")
    List<Apply> findApplyByApplyStatusType_Pendency(@Param("status") ApplyStatusType status);
}

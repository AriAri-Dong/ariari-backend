package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecruitmentBookmarkRepository extends JpaRepository<RecruitmentBookmark, Long> {

    Boolean existsByMemberAndRecruitment(Member member, Recruitment recruitment);

    Optional<RecruitmentBookmark> findByMemberAndRecruitment(Member member, Recruitment recruitment);

    @EntityGraph(attributePaths = "recruitment")
    Page<RecruitmentBookmark> findByMember(Member member, Pageable pageable);

    List<RecruitmentBookmark> findAllByRecruitment(Recruitment recruitment);

    Long countByRecruitment(Recruitment recruitment);

    @Query("select rb from RecruitmentBookmark rb " +
            "join fetch rb.recruitment r " +
            "join fetch rb.member m " +
            "where r.endDateTime between :startDate and :endDate")
    List<RecruitmentBookmark> findAllByWithinRecruitment(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

}

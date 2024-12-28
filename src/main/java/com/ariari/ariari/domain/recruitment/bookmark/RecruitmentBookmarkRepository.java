package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentBookmarkRepository extends JpaRepository<RecruitmentBookmark, Long> {

    Boolean existsByMemberAndRecruitment(Member member, Recruitment recruitment);

    Optional<RecruitmentBookmark> findByMemberAndRecruitment(Member member, Recruitment recruitment);

    @EntityGraph(attributePaths = "recruitment")
    Page<RecruitmentBookmark> findByMember(Member member, Pageable pageable);

    Long countByRecruitment(Recruitment recruitment);

}

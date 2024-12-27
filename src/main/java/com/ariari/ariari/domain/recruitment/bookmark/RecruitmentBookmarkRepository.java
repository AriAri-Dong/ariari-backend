package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentBookmarkRepository extends JpaRepository<RecruitmentBookmark, Long> {

    @EntityGraph(attributePaths = "recruitment")
    Page<RecruitmentBookmark> findByMember(Member member, Pageable pageable);

}

package com.ariari.ariari.domain.club.bookmark;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBookmarkRepository extends JpaRepository<ClubBookmark, Long> {

    @EntityGraph(attributePaths = "club")
    Page<ClubBookmark> findByMember(Member member, Pageable pageable);

}

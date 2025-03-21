package com.ariari.ariari.domain.club.bookmark;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubBookmarkRepository extends JpaRepository<ClubBookmark, Long> {

    @EntityGraph(attributePaths = "club")
    Page<ClubBookmark> findByMember(Member member, Pageable pageable);

    Boolean existsByMemberAndClub(Member member, Club club);

    Optional<ClubBookmark> findByMemberAndClub(Member member, Club club);

    List<ClubBookmark> findAllByClub(Club club);

    @Modifying
    @Query("delete From ClubBookmark cb where cb.member= :member")
    void deleteByMember(Member member);

}

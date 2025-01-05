package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    @EntityGraph(attributePaths = "club")
    List<ClubMember> findByMember(Member member);

    List<ClubMember> findByClub(Club club);

    Optional<ClubMember> findByClubAndMember(Club club, Member member);

    Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);
}

package com.ariari.ariari.domain.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    Page<ClubMember> findByMember(Member member, Pageable pageable);

    Page<ClubMember> findByClub(Club club, Pageable pageable);

    Optional<ClubMember> findByMemberAndClub(Member member, Club club);




}

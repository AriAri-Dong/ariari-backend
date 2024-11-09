package com.ariari.ariari.domain.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    @EntityGraph(attributePaths = "club")
    Page<ClubMember> findByMember(Member member, Pageable pageable);

    Page<ClubMember> findByClub(Club club, Pageable pageable);

    Optional<ClubMember> findByClubAndMember(Club club, Member member);

    @Query("select cm from ClubMember cm where cm.member= :member and cm.club.school is null")
    @EntityGraph(attributePaths = "club")
    Page<ClubMember> findExternalByMember(Member member, Pageable pageable);

    @Query("select cm from ClubMember cm where cm.member= :member and cm.club.school= :school")
    @EntityGraph(attributePaths = "club")
    Page<ClubMember> findInternalByMember(Member member, School school, Pageable pageable);

}

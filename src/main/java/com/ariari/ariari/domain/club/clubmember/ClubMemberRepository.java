package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>, ClubMemberRepositoryCustom {

    @EntityGraph(attributePaths = "club")
    List<ClubMember> findByMember(Member member);

    Optional<ClubMember> findByClubAndMember(Club club, Member member);


   //@Query("SELECT cm FROM ClubMember cm JOIN FETCH cm.club WHERE cm.club = :club")
    List<ClubMember> findAllByClub(Club club);

    @Query("select count(cm) from ClubMember as cm where cm.club = :club")
    Long countByClub(Club club);

    boolean existsByMemberAndClubMemberRoleType(Member member, ClubMemberRoleType memberRoleType);

    boolean existsByMemberIdAndClubId(Long  memberId, Long clubId);

    List<ClubMember> findByMemberAndClubMemberRoleTypeAndClub_SchoolIsNotNull(Member reqMember, ClubMemberRoleType clubMemberRoleType);
}

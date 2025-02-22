package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubMemberRepositoryCustom {

    Page<ClubMember> searchClubMember(Club club, ClubMemberStatusType statusType, String query, Pageable pageable);

    Page<ClubMember> findByClubAndNameContains(Club club, String query, Pageable pageable);

    boolean existsMyManagerOrHigher(Member member);

}

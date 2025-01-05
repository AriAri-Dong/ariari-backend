package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubMemberRepositoryCustom {

    List<ClubMember> findByClub(Club club, ClubMemberStatusType statusType, Pageable pageable);

}

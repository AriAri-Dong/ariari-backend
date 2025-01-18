package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubMemberRepositoryCustom {

    Page<ClubMember> findByClub(Club club, ClubMemberStatusType statusType, Pageable pageable);

}

package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInTeamSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplyRepositoryCustom {

    Page<Apply> searchApplyByClub(Club club, AppliesInTeamSearchCondition condition, Pageable pageable);

    Page<Apply> findFinalizedAppliesByMember(Member member, Pageable pageable);

}

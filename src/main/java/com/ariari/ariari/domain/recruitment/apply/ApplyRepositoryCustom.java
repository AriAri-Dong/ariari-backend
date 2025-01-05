package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInClubSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.req.MyAppliesSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplyRepositoryCustom {

    Page<Apply> searchApplyByClub(Club club, AppliesInClubSearchCondition condition, Pageable pageable);

    Page<Apply> searchByMember(Member member, MyAppliesSearchType searchType, Pageable pageable);

}

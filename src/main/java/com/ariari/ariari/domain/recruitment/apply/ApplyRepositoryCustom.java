package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.apply.dto.req.ApplySearchCondition;
import org.springframework.data.domain.Page;

public interface ApplyRepositoryCustom {

    Page<Apply> searchApplyByClub(Club club, ApplySearchCondition condition);


}

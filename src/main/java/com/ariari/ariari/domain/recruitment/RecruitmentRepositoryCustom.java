package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitmentRepositoryCustom {

    Page<Recruitment> searchRecruitmentPage(School school, ClubSearchCondition condition, Pageable pageable);

    Page<Recruitment> searchExternalPage(ClubSearchCondition condition, Pageable pageable);

    Page<Recruitment> searchInternalPage(School school, ClubSearchCondition condition, Pageable pageable);

    List<Recruitment> findExternalRecruitmentRankingList();

    List<Recruitment> findinternalRecruitmentRankingList(School school);

}

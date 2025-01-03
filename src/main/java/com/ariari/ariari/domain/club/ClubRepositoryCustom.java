package com.ariari.ariari.domain.club;

import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubRepositoryCustom {

    Page<Club> searchClubPage(School school, ClubSearchCondition condition, Pageable pageable);

    Page<Club> searchExternalPage(ClubSearchCondition condition, Pageable pageable);

    Page<Club> searchInternalPage(School school, ClubSearchCondition condition, Pageable pageable);

    Page<Club> findByNameContains(String query, School school, Pageable pageable);

    List<Club> findExternalClubRankingList(ClubCategoryType categoryType);

    List<Club> findInternalClubRankingList(School school, ClubCategoryType categoryType);

}

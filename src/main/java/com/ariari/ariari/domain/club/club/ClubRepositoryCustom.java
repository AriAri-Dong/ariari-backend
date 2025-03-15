package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.club.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubRepositoryCustom {

    Page<ClubData> searchClubPage(School school, ClubSearchCondition condition, Pageable pageable);

    Page<ClubData> searchExternalPage(ClubSearchCondition condition, Pageable pageable);

    Page<ClubData> searchInternalPage(School school, ClubSearchCondition condition, Pageable pageable);

    Page<ClubData> findByNameContains(String query, School school, Pageable pageable);

    Page<ClubData> findMyBookmarkClubs(Member member, Pageable pageable);

    List<Club> findExternalClubRankingList(ClubCategoryType categoryType);

    List<Club> findInternalClubRankingList(School school, ClubCategoryType categoryType);

}

package com.ariari.ariari.domain.club;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("select c from Club c where c.school is null and (:clubCategoryType is null or c.clubCategoryType= :clubCategoryType)")
    Page<Club> findExternalByClubCategoryType(ClubCategoryType clubCategoryType, Pageable pageable);

    @Query("select c from Club c where c.school= :school and (:clubCategoryType is null or c.clubCategoryType= :clubCategoryType)")
    Page<Club> findInternalByClubCategoryType(School school, ClubCategoryType clubCategoryType, Pageable pageable);

}

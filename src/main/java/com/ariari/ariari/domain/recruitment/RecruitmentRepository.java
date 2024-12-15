package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    @Query("select r from Recruitment r where r.club.school is null and (:clubCategoryType is null or r.club.clubCategoryType= :clubCategoryType)")
    Page<Recruitment> findExternalByClubCategoryType(ClubCategoryType clubCategoryType, Pageable pageable);

    @Query("select r from Recruitment r where r.club.school= :school and (:clubCategoryType is null or r.club.clubCategoryType= :clubCategoryType)")
    Page<Recruitment> findInternalByClubCategoryType(School school, ClubCategoryType clubCategoryType, Pageable pageable);

}

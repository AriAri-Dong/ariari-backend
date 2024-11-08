package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    @Query("select r from Recruitment r where r.club.school is null")
    Page<Recruitment> findExternal(Pageable pageable);

    @Query("select r from Recruitment r where r.club.school= :school")
    Page<Recruitment> findInternal(School school, Pageable pageable);

    @Query("select r from Recruitment r where r.club.school is null and r.club.clubCategoryType= :clubCategoryType")
    Page<Recruitment> findExternalByClubCategoryType(ClubCategoryType clubCategoryType, Pageable pageable);

    @Query("select r from Recruitment r where r.club.school= :school and r.club.clubCategoryType= :clubCategoryType")
    Page<Recruitment> findInternalByClubCategoryType(School school, ClubCategoryType clubCategoryType, Pageable pageable);

    @Query("select r from Recruitment r where r.club.school is null order by r.views desc limit 15")
    List<Recruitment> findExternalRanking();

    @Query("select r from Recruitment r where r.club.school= :school order by r.views desc limit 15")
    List<Recruitment> findInternalRanking(School school);


}

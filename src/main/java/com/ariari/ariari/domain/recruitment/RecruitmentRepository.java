package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    @Query("select r from Recruitment r where r.deletedDateTime is null and r.club.school is null order by r.views desc limit 15")
    List<Recruitment> findRanking();

    @Query("select r from Recruitment r where r.deletedDateTime is null and r.club.school= :school order by r.views desc limit 15")
    List<Recruitment> findRankingBySchool(School school);

    @Query("select r from Recruitment r where r.deletedDateTime is null and r.club.school is null")
    Page<Recruitment> findPage(Pageable pageable);

    @Query("select r from Recruitment r where r.deletedDateTime is null and r.club.school is null and r.club.clubCategoryType= :clubCategoryType")
    Page<Recruitment> findPageByClubCategoryType(ClubCategoryType clubCategoryType, Pageable pageable);

    @Query("select r from Recruitment r where r.deletedDateTime is null and r.club.school= :school and r.club.clubCategoryType= :clubCategoryType")
    Page<Recruitment> findPageBySchoolAndClubCategoryType(School school, ClubCategoryType clubCategoryType, Pageable pageable);

    Page<Recruitment> findByClub_ClubCategoryType(ClubCategoryType clubCategoryType, Pageable pageable);

    @Query("select r from Recruitment r where r.deletedDateTime is null")
    Page<Recruitment> findActivatedAll(Pageable pageable);

}

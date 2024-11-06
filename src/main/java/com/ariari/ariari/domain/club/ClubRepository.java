package com.ariari.ariari.domain.club;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("select c from Club c where c.deletedDateTime is null and c.school is null order by c.views desc limit 9")
    List<Club> findRanking();

    @Query("select c from Club c where c.deletedDateTime is null and c.school is null and c.clubCategoryType= :clubCategoryType order by c.views desc limit 9")
    List<Club> findRankingByClubCategoryType(ClubCategoryType clubCategoryType);

    @Query("select c from Club c where c.deletedDateTime is null and c.school= :school order by c.views desc limit 9")
    List<Club> findRankingBySchool(School school);

    @Query("select c from Club c where c.deletedDateTime is null and c.school= :school and c.clubCategoryType= :clubCategoryType order by c.views desc limit 9")
    List<Club> findRankingBySchoolAndClubCategoryType(School school, ClubCategoryType clubCategoryType);

    @Query("select c from Club c where c.deletedDateTime is null")
    List<Club> findActivatedAll();


}

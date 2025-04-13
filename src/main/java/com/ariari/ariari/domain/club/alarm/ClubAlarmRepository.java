package com.ariari.ariari.domain.club.alarm;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import io.lettuce.core.dynamic.annotation.Param;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubAlarmRepository extends JpaRepository<ClubAlarm, Long> {

    @Query("SELECT ca FROM ClubAlarm ca WHERE ca.club = :club ORDER BY ca.createdDateTime DESC")
    Page<ClubAlarm> findAllByClub(@Param("club") Club club, Pageable pageable);

    Optional<ClubAlarm> findByIdAndClub(Long id, Club clubId);

    @Query("select count(ca) from ClubAlarm  as ca where ca.club = :club and ca.isChecked = false " )
    Integer countUnreadByClub(@Param("club") Club club);
}

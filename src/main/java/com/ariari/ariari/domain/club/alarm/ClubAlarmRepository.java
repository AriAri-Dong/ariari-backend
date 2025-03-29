package com.ariari.ariari.domain.club.alarm;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubAlarmRepository extends JpaRepository<ClubAlarm, Long> {

    Page<ClubAlarm> findAllByClub(Club club, Pageable pageable);

    Optional<ClubAlarm> findByIdAndClub(Long id, Club clubId);
}

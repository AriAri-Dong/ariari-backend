package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.event.ClubEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findTop3ByClubEvent(ClubEvent clubEvent);

    Long countByClubEvent(ClubEvent clubEvent);

    Optional<Attendance> findByClubEventAndClubMember(ClubEvent clubEvent, ClubMember clubMember);

}

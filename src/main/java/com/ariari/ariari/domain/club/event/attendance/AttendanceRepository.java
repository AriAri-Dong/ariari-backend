package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.ariari.ariari.domain.member.QMember.member;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findTop3ByClubEvent(ClubEvent clubEvent);

    Long countByClubEvent(ClubEvent clubEvent);

    Optional<Attendance> findByClubEventAndMember(ClubEvent clubEvent, Member member);

    Page<Attendance> findByClubEvent(ClubEvent clubEvent, Pageable pageable);



    @Modifying
    @Query(value = """
    UPDATE attendance a
    JOIN club_event ce ON a.club_event_id = ce.club_event_id
    SET a.member_id = null
    WHERE ce.club_id = :clubId
      AND a.member_id = :memberId
    """, nativeQuery = true)
    void attendanceUpdate(@Param("clubId") Long clubId, @Param("memberId") Long memberId);

}

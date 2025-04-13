package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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


    @Query("select a from Attendance a join fetch a.clubEvent ce where a.member = :member and ce.club = :club ")
    List<Attendance> findAllByClubAndMember(@Param("club") Club club,
                                            @Param("member") Member member);
}

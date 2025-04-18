package com.ariari.ariari.domain.club.activity;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubActivityRepository extends JpaRepository<ClubActivity, Long> {

    Page<ClubActivity> findByClub(Club club, Pageable pageable);



    Optional<ClubActivity> findFirstByClubOrderByCreatedDateTimeDesc(Club club);

    @Modifying
    @Query("update ClubActivity as ca set ca.member = null where ca.club= :club and ca.member = :member")
    void clubActivityUpdate(@Param("club") Club club, @Param("member") Member member);
}

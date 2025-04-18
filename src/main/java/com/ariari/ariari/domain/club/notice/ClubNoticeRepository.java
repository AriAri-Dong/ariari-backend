package com.ariari.ariari.domain.club.notice;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubNoticeRepository extends JpaRepository<ClubNotice, Long> {

    @Query("select cn from ClubNotice cn where cn.isFixed= true and cn.club= :club")
    List<ClubNotice> findFixedByClub(Club club);

    @Query("select cn from ClubNotice cn where cn.isFixed= false and cn.club= :club")
    Page<ClubNotice> findUnfixedByClub(Club club, Pageable pageable);


    Optional<ClubNotice> findFirstByClubOrderByCreatedDateTimeDesc(Club club);

    @Modifying
    @Query("update ClubNotice as cn set cn.member = null where cn.club= :club and cn.member = :member")
    void clubNoticeUpdate(@Param("club") Club club, @Param("member") Member member);
}

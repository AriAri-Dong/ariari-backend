package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.member.Member;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface MemberAlarmRepository extends JpaRepository<MemberAlarm, Long>, MemberAlarmRepositoryCustom {

    Optional<MemberAlarm> findByIdAndMemberId(Long id, Long memberId);

    @Query("SELECT ma FROM MemberAlarm ma WHERE ma.member = :member ORDER BY ma.createdDateTime DESC")
    Page<MemberAlarm> findAllByMember(@Param("member") Member member, Pageable pageable);

    @Query("select count(*) from MemberAlarm  as ma where ma.member = :member and ma.isChecked=false")
    Integer countUnreadByMember(@Param("member") Member member);

}

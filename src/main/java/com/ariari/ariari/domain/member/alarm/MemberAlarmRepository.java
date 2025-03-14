package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.member.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MemberAlarmRepository extends JpaRepository<MemberAlarm, Long> {

    Optional<MemberAlarm> findByIdAndMemberId(Long id, Long memberId);
    Page<MemberAlarm> findAllByMember(Member member, Pageable pageable);
    Optional<MemberAlarm> findByUri(String uri);
}

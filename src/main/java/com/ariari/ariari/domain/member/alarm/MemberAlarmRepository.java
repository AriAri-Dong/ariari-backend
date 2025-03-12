package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberAlarmRepository extends JpaRepository<MemberAlarm, Long> {

    Optional<MemberAlarm> findByIdAndMemberId(Long id, Long memberId);
    List<MemberAlarm> findAllByMember(Member member);
    Optional<MemberAlarm> findByUri(String uri);
}

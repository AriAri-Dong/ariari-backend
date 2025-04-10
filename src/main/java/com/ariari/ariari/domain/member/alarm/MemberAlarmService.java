package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.alarm.ClubAlarm;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.dto.res.MemberAlarmListRes;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEvent;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEventList;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAlarmService {

    private final MemberRepository memberRepository;
    private final MemberAlarmRepository memberAlarmRepository;

    @Transactional(readOnly = true)
    public MemberAlarmListRes getAlarms(Long memberId, Pageable pageable) {
        Member reqMember = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        Page<MemberAlarm> memberAlarmsPage = memberAlarmRepository.findAllByMember(reqMember, pageable);
        Integer unreadCount = memberAlarmRepository.countUnreadByMember(reqMember);

        return MemberAlarmListRes.fromPage(memberAlarmsPage, unreadCount);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void saveAlarms(MemberAlarmEvent memberAlarmEvent){

        // 단일 이벤트 처리
        MemberAlarm memberAlarm = MemberAlarm.builder()
                .title(memberAlarmEvent.getTitle())
                .member(memberAlarmEvent.getMember())
                .uri(memberAlarmEvent.getUri())
                .isChecked(false)
                .build();
        // 알람 저장
        memberAlarmRepository.save(memberAlarm);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void saveAlarms(MemberAlarmEventList memberAlarmListEvent){
        // 다중 이벤트 처리
        List<MemberAlarm> memberAlarms = memberAlarmListEvent.getMemberAlarmEventList().stream()
                .map( memberAlarmEvent -> MemberAlarm.builder()
                .title(memberAlarmEvent.getTitle())
                .uri(memberAlarmEvent.getUri())
                .member(memberAlarmEvent.getMember())
                .isChecked(false)
                        .build())
                .toList();
        // 알림 저장
        // 배치 처리 해야할까?
        memberAlarmRepository.saveAll(memberAlarms);
    }

    @Transactional
    public void readAlarm(Long memberId, Long alarmId) {
        // 알림을 조회하여 없으면 예외 처리
        MemberAlarm memberAlarm = memberAlarmRepository.findByIdAndMemberId(alarmId, memberId)
                .orElseThrow(NotFoundEntityException::new);
        // 읽음 처리
        if(!memberAlarm.getIsChecked()) {
            memberAlarm.MarkRead();
        }
    }

    @Transactional
    public void removeAlarm(Long memberId, Long alarmId) {
        // 알림을 조회하여 없으면 예외 처리
        MemberAlarm memberAlarm = memberAlarmRepository.findByIdAndMemberId(alarmId, memberId)
                .orElseThrow(NotFoundEntityException::new);
        // 알림을 논리 삭제 처리
        memberAlarmRepository.delete(memberAlarm);
    }
}

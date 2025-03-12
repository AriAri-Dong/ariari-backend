package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.dto.MemberAlarmData;
import com.ariari.ariari.domain.member.alarm.dto.res.MemberAlarmListRes;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEvent;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberAlarmService {

    private final MemberRepository memberRepository;
    private final MemberAlarmRepository memberAlarmRepository;

    @Transactional(readOnly = true)
    public MemberAlarmListRes getAlarms(Long memberId) {
        Member reqMember = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);
        List<MemberAlarm> memberAlarms = memberAlarmRepository.findAllByMember(reqMember);

        List<MemberAlarmData> memberAlarmDataList = memberAlarms.stream()
                .map( memberAlarm -> MemberAlarmData.builder()
                        .id(memberAlarm.getId())
                        .title(memberAlarm.getTitle())
                        .body(memberAlarm.getBody())
                        .extraBody(memberAlarm.getExtraBody())
                        .memberAlarmType(memberAlarm.getMemberAlarmType())
                        .uri(memberAlarm.getUri())
                        .isChecked(memberAlarm.getIsChecked())
                        .createdDateTime(memberAlarm.getCreatedDateTime())
                        .build())
                .collect(Collectors.toList());

        return MemberAlarmListRes.from(memberAlarmDataList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void saveAlarms(MemberAlarmEvent memberAlarmEvent){
        // Q&A 답변 알람 생성
        MemberAlarm memberAlarm = MemberAlarm.builder()
                .title(memberAlarmEvent.getTitle())
                .body(memberAlarmEvent.getBody())
                .extraBody(memberAlarmEvent.getExtraBody())
                .memberAlarmType(memberAlarmEvent.getMemberAlarmType())
                .member(memberAlarmEvent.getMember())
                .uri(memberAlarmEvent.getUri())
                .isChecked(false)
                .build();
        // 알람 저장
        memberAlarmRepository.save(memberAlarm);
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

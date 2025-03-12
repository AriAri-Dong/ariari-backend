package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import com.ariari.ariari.domain.member.alarm.MemberAlarmRepository;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEvent;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberAlarmManger {

    private final ApplicationEventPublisher eventPublisher;
    private final MemberAlarmRepository memberAlarmRepository;

    public void sendClubAnswerAlarm(ClubQuestion clubQuestion){
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                "test",
                "test",
                "",
                "/clubs/" + clubQuestion.getClub().getId() + "/club-questions/",
                MemberAlarmType.CLUB,
                clubQuestion.getMember()
        );
        send(memberAlarmEvent);
    }

    public void sendApplyTempEvent(ApplyTemp applyTemp){
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                "임시저장된 지원서 모집마감임박(D-1) 알림",
                "임시 저장된 지원서의 모집 마감이 하루 남았습니다! 오늘 안에 제출을 완료해 주세요.",
                "",
                "/apply-temps/" + applyTemp.getId(),
                MemberAlarmType.APPLY,
                applyTemp.getMember()
        );
        send(memberAlarmEvent);
    }

    private void send(MemberAlarmEvent memberAlarmEvent){
        eventPublisher.publishEvent(memberAlarmEvent);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteApplyTemp(ApplyTemp applyTemp){
        try {
            MemberAlarm memberAlarm = memberAlarmRepository.findByUri("/apply-temps/" + applyTemp.getId()).orElseThrow(NotFoundEntityException::new);
            memberAlarmRepository.delete(memberAlarm);
        }catch (Exception e){
            log.error("임시 지원서 알림 삭제 실패 : {}",e);
        }
    }


}

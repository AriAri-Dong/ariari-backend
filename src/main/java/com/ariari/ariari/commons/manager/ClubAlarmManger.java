package com.ariari.ariari.commons.manager;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.enums.ClubAlarmType;
import com.ariari.ariari.domain.club.alarm.event.ClubAlarmEvent;
import com.ariari.ariari.domain.club.alarm.event.ClubAlarmEventList;
import com.ariari.ariari.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClubAlarmManger {

    private final ApplicationEventPublisher eventPublisher;

    public void sendRecruitmentReminderD1AndD7(List<Club> clubList, int check){
        if(check == 1){
            ClubAlarmEventList clubAlarmEventList = ClubAlarmEventList.from("모집 마감이 하루 남았습니다! 아리아리와 함께 동아리 모집을 성공적으로 마무리해 보세요."
                    ,"recruitments", ClubAlarmType.RECRUITMENT,clubList);
            sendList(clubAlarmEventList);
        }else if(check == 7){
            ClubAlarmEventList clubAlarmEventList = ClubAlarmEventList.from("곧 모집이 마감됩니다! 모집을 성공적으로 마치기 위해 지원 현황을 한 번 더 확인해 보세요."
                    ,"recruitments", ClubAlarmType.RECRUITMENT,clubList);
            sendList(clubAlarmEventList);
        }

    }

    public void sendRecruitmentMember(Member member, Club club, String recruitmentTitle) {
        String title = String.format("%s님이 %s에 지원했습니다! 지원 현황을 바로 확인해 보세요.", member.getNickName(), recruitmentTitle);
        ClubAlarmEvent clubAlarmEvent = ClubAlarmEvent.from(
            title, "recruitments", ClubAlarmType.APPLY, club
        );
        sendSingle(clubAlarmEvent);
    }

    public void sendUncheckMember(List<Club> clubList, String recruitmentTitle){
        String title = String.format("%s에 아직 검토되지 않은 지원서가 있어요! 확인해 보세요.", recruitmentTitle);
        ClubAlarmEventList clubAlarmEventList = ClubAlarmEventList.from(title
        , "recruitments", ClubAlarmType.APPLY, clubList);
        sendList(clubAlarmEventList);
    }

    public void sendClubQA(Club club){
        String title = "Q&A에 새로운 질문이 등록되었습니다! 질문 내용을 확인하고 도움을 제공해 보세요.";
        ClubAlarmEvent clubAlarmEvent = ClubAlarmEvent.from(title,
                "clubs/"+club.getId()+"/club-questions", ClubAlarmType.QUESTION, club);
        sendSingle(clubAlarmEvent);
    }


    private void sendSingle(ClubAlarmEvent clubAlarmEvent){
        eventPublisher.publishEvent(clubAlarmEvent);
    }

    private void sendList(ClubAlarmEventList clubAlarmEventList){
        eventPublisher.publishEvent(clubAlarmEventList);
    }
}

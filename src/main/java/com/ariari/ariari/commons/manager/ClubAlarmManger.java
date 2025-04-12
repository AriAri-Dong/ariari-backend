package com.ariari.ariari.commons.manager;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.event.ClubAlarmEvent;
import com.ariari.ariari.domain.club.alarm.event.ClubAlarmEventList;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.dto.ApplyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClubAlarmManger {

    private final ApplicationEventPublisher eventPublisher;

    public void sendRecruitmentReminderD1AndD7(Map<Integer,List<Club>> recruitmentMap){
        ClubAlarmEventList clubAlarmEventList = ClubAlarmEventList.from(
                recruitmentMap.entrySet().stream()
                        .flatMap(entry -> {
                            int day = entry.getKey();
                            return entry.getValue().stream()
                                    .map(club -> {
                                        String title = (day == 1)
                                                ? "모집 마감이 하루 남았습니다! 아리아리와 함께 동아리 모집을 성공적으로 마무리해 보세요."
                                                : "곧 모집이 마감됩니다! 모집을 성공적으로 마치기 위해 지원 현황을 한 번 더 확인해 보세요.";

                                        String uri = "/club/management/recruitment/applicationStatus?clubId=" + club.getId();
                                        return ClubAlarmEvent.from(title, uri, club);
                                    });
                        })
                        .toList()
        );

        sendList(clubAlarmEventList);
    }

    // 지원
    public void sendRecruitmentMember(Member member, Club club, String recruitmentTitle) {
        String title = String.format("%s님이 %s에 지원했습니다! 지원 현황을 바로 확인해 보세요.", member.getNickName(), recruitmentTitle);
        Long clubId = club.getId();
        ClubAlarmEvent clubAlarmEvent = ClubAlarmEvent.from(
            title, "/club/management/recruitment/applicationStatus?clubId="+clubId, club
        );
        sendSingle(clubAlarmEvent);
    }

    public void sendUncheckApply(Map<Long, ApplyInfo> applyMap){
        List<ClubAlarmEvent> clubList = applyMap.values().stream()
                .map(ApplyInfo::toAlarmEvent)
                .toList();
        ClubAlarmEventList clubAlarmEventList = ClubAlarmEventList.from(clubList);
        sendList(clubAlarmEventList);
    }

    public void sendClubQA(Club club){
        String title = "Q&A에 새로운 질문이 등록되었습니다! 질문 내용을 확인하고 도움을 제공해 보세요.";
        Long clubId = club.getId();
        ClubAlarmEvent clubAlarmEvent = ClubAlarmEvent.from(title,
                "/club/help?clubId="+clubId,  club);
        sendSingle(clubAlarmEvent);
    }

    //멤버 탈퇴 알림
    public void quitClubMember(String clubMemberName, Club club, LocalDateTime quitDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDate = quitDateTime.format(formatter);
        String title = String.format("%s, %s님이 동아리에서 탈퇴하셨습니다. 동아리 상황을 확인하고 필요한 조치를 취해 보세요.",
                formattedDate, clubMemberName);

        Long clubId = club.getId();
        ClubAlarmEvent clubAlarmEvent = ClubAlarmEvent.from(title,
                "/club/help?clubId="+clubId,
                club);
        sendSingle(clubAlarmEvent);

    }


    // 시스템 알림
    // 활동 내역 알림


    private void sendSingle(ClubAlarmEvent clubAlarmEvent){
        eventPublisher.publishEvent(clubAlarmEvent);
    }

    private void sendList(ClubAlarmEventList clubAlarmEventList){
        eventPublisher.publishEvent(clubAlarmEventList);
    }
}

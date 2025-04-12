package com.ariari.ariari.domain.recruitment.apply.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.event.ClubAlarmEvent;
import lombok.Getter;

@Getter
public class ApplyInfo {

    private final String title;
    private final Club club;

    private ApplyInfo(String title, Club club) {
        this.title = title;
        this.club = club;
    }

    public static ApplyInfo from(String title, Club club) {
        return new ApplyInfo(title, club);
    }

    public ClubAlarmEvent toAlarmEvent() {
        String message = String.format("%s에 아직 검토되지 않은 지원서가 있어요! 확인해 보세요.", title);
        String uri = "/club/management/recruitment/applicationStatus?clubId=" + club.getId();
        return ClubAlarmEvent.from(message, uri, club);
    }
}

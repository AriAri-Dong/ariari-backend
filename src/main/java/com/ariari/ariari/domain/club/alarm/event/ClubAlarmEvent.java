package com.ariari.ariari.domain.club.alarm.event;


import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.enums.ClubAlarmType;

import lombok.Getter;

@Getter
public class ClubAlarmEvent {

    private final String title;
    private final String uri;
    private final ClubAlarmType clubAlarmType;
    private final Club club;


    private ClubAlarmEvent(String title, String uri, ClubAlarmType clubAlarmType, Club club) {
        this.title = title;
        this.uri = uri;
        this.clubAlarmType = clubAlarmType;
        this.club = club;
    }

    public static ClubAlarmEvent from(String title, String uri, ClubAlarmType clubAlarmType, Club club) {
        return new ClubAlarmEvent(title, uri, clubAlarmType, club);
    }
}

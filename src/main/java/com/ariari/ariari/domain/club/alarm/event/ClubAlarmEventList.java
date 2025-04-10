package com.ariari.ariari.domain.club.alarm.event;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.enums.ClubAlarmType;

import lombok.Getter;

import java.util.List;

@Getter
public class ClubAlarmEventList {

    private final List<ClubAlarmEvent> clubAlarmEventList;

    private ClubAlarmEventList(List<ClubAlarmEvent> clubAlarmEventList) {
        this.clubAlarmEventList = clubAlarmEventList;
    }

    public static ClubAlarmEventList from(String title, String uri, List<Club> clubs){
        List<ClubAlarmEvent> clubAlarmEvents = clubs.stream()
                .map( club -> ClubAlarmEvent.from(title, uri, club))
                .toList();
        return new ClubAlarmEventList(clubAlarmEvents);
    }
}

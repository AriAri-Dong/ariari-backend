package com.ariari.ariari.domain.club.alarm.event;

import lombok.Getter;

import java.util.List;

@Getter
public class ClubAlarmEventList {

    private final List<ClubAlarmEvent> clubAlarmEventList;

    private ClubAlarmEventList(List<ClubAlarmEvent> clubAlarmEventList) {
        this.clubAlarmEventList = clubAlarmEventList;
    }

    public static ClubAlarmEventList from(List<ClubAlarmEvent> clubAlarmEventList){
        return new ClubAlarmEventList(clubAlarmEventList);
    }

}

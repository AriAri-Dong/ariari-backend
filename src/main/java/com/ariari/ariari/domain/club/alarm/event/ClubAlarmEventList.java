package com.ariari.ariari.domain.club.alarm.event;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.enums.ClubAlarmType;

import lombok.Getter;

import java.util.List;
import java.util.Map;

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

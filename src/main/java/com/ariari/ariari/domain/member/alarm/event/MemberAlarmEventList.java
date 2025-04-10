package com.ariari.ariari.domain.member.alarm.event;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberAlarmEventList {

    private final List<MemberAlarmEvent> memberAlarmEventList;

    private MemberAlarmEventList(List<MemberAlarmEvent> memberAlarmEventList) {
        this.memberAlarmEventList = memberAlarmEventList;
    }

    public static MemberAlarmEventList from(String title, String uri, List<Member> members){
        List<MemberAlarmEvent> memberAlarmEvents = members.stream()
                .map( member -> MemberAlarmEvent.from(title, uri, member))
                .toList();
        return new MemberAlarmEventList(memberAlarmEvents);
    }
}

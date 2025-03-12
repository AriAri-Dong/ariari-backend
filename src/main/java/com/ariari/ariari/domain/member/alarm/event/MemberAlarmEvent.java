package com.ariari.ariari.domain.member.alarm.event;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberAlarmEvent {

    private final String title;
    private final String body;
    private final String extraBody;
    private final String uri;
    private final MemberAlarmType memberAlarmType;
    private final Member member;


    private MemberAlarmEvent(String title, String body, String extraBody, String uri, MemberAlarmType memberAlarmType, Member member) {
        this.title = title;
        this.body = body;
        this.extraBody = extraBody;
        this.uri = uri;
        this.memberAlarmType = memberAlarmType;
        this.member = member;
    }

    public static MemberAlarmEvent from(String title, String body, String extraBody, String uri, MemberAlarmType memberAlarmType, Member member) {
        return new MemberAlarmEvent(title, body, extraBody, uri, memberAlarmType, member);
    }


}

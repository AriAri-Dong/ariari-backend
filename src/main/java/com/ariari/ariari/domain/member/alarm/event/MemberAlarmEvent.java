package com.ariari.ariari.domain.member.alarm.event;

import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import com.ariari.ariari.domain.club.question.answer.dto.ClubAnswerData;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberAlarmEvent {

    private String title;

    private String body;

    private String extraBody;

    private String uri;

    private MemberAlarmType memberAlarmType;

    private Member member;

    @Builder
    public MemberAlarmEvent(String title, String body, String extraBody, String uri, MemberAlarmType memberAlarmType, Member member) {
        this.title = title;
        this.body = body;
        this.extraBody = extraBody;
        this.uri = uri;
        this.memberAlarmType = memberAlarmType;
        this.member = member;
    }


}

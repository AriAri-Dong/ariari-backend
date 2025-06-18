package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.club.Club;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MemberAlarmRepositoryImpl implements MemberAlarmRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public void deleteAlarmsByClubId(Club club, Long memberId, String keyword) {
        QMemberAlarm ma = QMemberAlarm.memberAlarm;

        jpaQueryFactory
                .update(ma)
                .set(ma.deletedDateTime, LocalDateTime.now())
                .where(
                        ma.member.id.eq(memberId)
                                .and(ma.uri.like("% | " + keyword))
                )
                .execute();
    }
}

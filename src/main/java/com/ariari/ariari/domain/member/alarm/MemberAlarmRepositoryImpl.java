package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.QClub;
import com.ariari.ariari.domain.club.clubmember.QClubMember;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MemberAlarmRepositoryImpl implements MemberAlarmRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public void deleteAlarmsByClubId(Club club, String keyword) {
        QMemberAlarm ma = QMemberAlarm.memberAlarm;
        QClubMember cm = QClubMember.clubMember;

        jpaQueryFactory
                .update(ma)
                .set(ma.deletedDateTime, LocalDateTime.now())
                .where(ma.member.id.in(
                        JPAExpressions.select(cm.member.id)
                                .from(cm)
                                .where(cm.club.eq(club))
                        )
                        .and(ma.title.contains(keyword))
                )
                .execute();

    }
}

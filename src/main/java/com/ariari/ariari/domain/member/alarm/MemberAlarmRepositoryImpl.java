package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.QClub;
import com.ariari.ariari.domain.club.clubmember.QClubMember;
import com.ariari.ariari.domain.member.Member;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MemberAlarmRepositoryImpl implements MemberAlarmRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public void deleteAlarmsByClubId(Long reqMemberId, Club club, String keyword) {
        QMemberAlarm ma = QMemberAlarm.memberAlarm;
        QClubMember cm = QClubMember.clubMember;

        jpaQueryFactory
                .update(ma)
                .set(ma.deletedDateTime, LocalDateTime.now())
                .where(
                        ma.member.id.eq(reqMemberId)   // 1) 알림 소유자가 요청한 회원인지
                                .and(
                                        JPAExpressions.selectOne()
                                                .from(cm)
                                                .where(cm.club.eq(club)  // 2) 해당 동아리인지
                                                        .and(cm.member.id.eq(ma.member.id)) // 3) 그리고 알림 주인이 동아리에 소속된 회원인지
                                                )
                                                .exists()                   // 서브쿼리로 존재 여부 확인
                                )
                                .and(ma.title.contains(keyword))      // 4) 알림 제목에 키워드 포함 (예: "초대장")
                )
                .execute();

    }
}

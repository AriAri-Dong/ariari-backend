package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ariari.ariari.domain.club.clubmember.QClubMember.clubMember;

@RequiredArgsConstructor
public class ClubMemberRepositoryImpl implements ClubMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ClubMember> findByClub(Club club, ClubMemberStatusType statusType, Pageable pageable) {
        return queryFactory.selectFrom(clubMember)
                .where(clubEq(club),
                        statusTypeEq(statusType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression clubEq(Club club) {
        return clubMember.club.eq(club);
    }

    private BooleanExpression statusTypeEq(ClubMemberStatusType statusType) {
        return statusType == null ? null : clubMember.clubMemberStatusType.eq(statusType);
    }

}

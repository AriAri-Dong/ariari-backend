package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.ariari.ariari.domain.club.QClub.club;
import static com.ariari.ariari.domain.club.clubmember.QClubMember.clubMember;

@RequiredArgsConstructor
public class ClubMemberRepositoryImpl implements ClubMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ClubMember> findByClub(Club club, ClubMemberStatusType statusType, Pageable pageable) {
        JPAQuery<ClubMember> query = queryFactory
                .selectFrom(clubMember)
                .where(clubEq(club),
                        statusTypeEq(statusType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(clubMember.getType(), clubMember.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ClubMember> content = query.fetch();

        Long total = queryFactory
                .select(clubMember.count())
                .from(clubMember)
                .where(clubEq(club),
                        statusTypeEq(statusType))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ClubMember> findByClubAndNameContains(Club club, String name, Pageable pageable) {
        JPAQuery<ClubMember> query = queryFactory
                .selectFrom(clubMember)
                .where(clubEq(club),
                        nameEq(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(clubMember.getType(), clubMember.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ClubMember> content = query.fetch();

        Long total = queryFactory
                .select(clubMember.count())
                .from(clubMember)
                .where(clubEq(club),
                        nameEq(name))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression clubEq(Club club) {
        return clubMember.club.eq(club);
    }

    private BooleanExpression statusTypeEq(ClubMemberStatusType statusType) {
        return statusType == null ? null : clubMember.clubMemberStatusType.eq(statusType);
    }

    private BooleanExpression nameEq(String name) {
        return name == null ? null : clubMember.name.eq(name);
    }

}

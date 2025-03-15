package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.QClubBookmark;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.club.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.club.enums.ParticipantType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.school.QSchool;
import com.ariari.ariari.domain.school.School;
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

import static com.ariari.ariari.domain.club.QClub.*;
import static com.ariari.ariari.domain.club.bookmark.QClubBookmark.*;
import static com.ariari.ariari.domain.school.QSchool.*;
import static com.ariari.ariari.domain.school.QSchool.school;

@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final int SIZE_OF_CLUB_RANKING_LIST = 9;

    @Override
    public Page<ClubData> searchClubPage(School schoolParam, ClubSearchCondition condition, Pageable pageable) {
        JPAQuery<ClubData> query = queryFactory
                .select(ClubData.projection())
                .from(club)
                .leftJoin(club.school, school).on(school.id.eq(club.school.id).or(club.school.isNull()))
                .where(schoolIsNull()
                                .or(schoolEq(schoolParam)),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(club.getType(), club.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<ClubData> content = query.fetch();

        Long total = queryFactory
                .select(club.count())
                .from(club)
                .where(schoolIsNull()
                                .or(schoolEq(schoolParam)),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ClubData> searchExternalPage(ClubSearchCondition condition, Pageable pageable) {
        JPAQuery<ClubData> query = queryFactory
                .select(ClubData.projection())
                .from(club)
                .leftJoin(club.school, school).on(school.isNull())
                .where(schoolIsNull(),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(club.getType(), club.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ClubData> content = query.fetch();

        Long total = queryFactory
                .select(club.count())
                .from(club)
                .where(schoolIsNull(),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ClubData> searchInternalPage(School school, ClubSearchCondition condition, Pageable pageable) {
        JPAQuery<ClubData> query = queryFactory
                .select(ClubData.projection())
                .from(club)
                .join(club.school)
                .where((schoolEq(school)),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(club.getType(), club.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ClubData> content = query.fetch();

        Long total = queryFactory
                .select(club.count())
                .from(club)
                .where(schoolEq(school),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ClubData> findByNameContains(String query, School schoolParam, Pageable pageable) {
        JPAQuery<ClubData> jpaQuery = queryFactory
                .select(ClubData.projection())
                .from(club)
                .leftJoin(club.school, school).on(school.id.eq(club.school.id).or(club.school.isNull()))
                .where(schoolIsNull()
                                .or(schoolEq(schoolParam)),
                        club.name.contains(query))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(club.getType(), club.getMetadata());
            jpaQuery.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ClubData> content = jpaQuery.fetch();

        Long total = queryFactory
                .select(club.count())
                .from(club)
                .where(schoolIsNull()
                                .or(schoolEq(schoolParam)),
                        club.name.contains(query))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ClubData> findMyBookmarkClubs(Member member, Pageable pageable) {
        JPAQuery<ClubData> jpaQuery = queryFactory
                .select(ClubData.projection())
                .from(club)
                .leftJoin(club.school, school).on(club.school.eq(school))
                .join(club.clubBookmarks, clubBookmark).on(clubBookmark.member.eq(member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(club.getType(), club.getMetadata());
            jpaQuery.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<ClubData> content = jpaQuery.fetch();

        Long total = queryFactory
                .select(club.count())
                .from(club)
                .leftJoin(club.school, school).on(club.school.eq(school))
                .join(club.clubBookmarks, clubBookmark).on(clubBookmark.club.id.eq(club.id).and(clubBookmark.member.id.eq(member.getId())))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<Club> findExternalClubRankingList(ClubCategoryType categoryType) {
        return queryFactory
                .selectFrom(club)
                .where(schoolIsNull(),
                        categoryEq(categoryType))
                .orderBy(club.views.desc())
                .limit(SIZE_OF_CLUB_RANKING_LIST)
                .fetch();
    }

    @Override
    public List<Club> findInternalClubRankingList(School school, ClubCategoryType categoryType) {
        return queryFactory
                .selectFrom(club)
                .where(schoolEq(school),
                        categoryEq(categoryType))
                .orderBy(club.views.desc())
                .limit(SIZE_OF_CLUB_RANKING_LIST)
                .fetch();
    }

    private BooleanExpression schoolEq(School school) {
        return school == null ? null : club.school.eq(school);
    }

    private BooleanExpression schoolIsNull() {
        return club.school.isNull();
    }

    private BooleanExpression categoryIn(List<ClubCategoryType> categoryTypes) {
        return categoryTypes.isEmpty() ? null : club.clubCategoryType.in(categoryTypes);
    }

    private BooleanExpression regionIn(List<ClubRegionType> regionTypes) {
        return regionTypes.isEmpty() ? null : club.clubRegionType.in(regionTypes);
    }

    private BooleanExpression participantIn(List<ParticipantType> participantTypes) {
        return participantTypes.isEmpty() ? null : club.participantType.in(participantTypes);
    }

    private BooleanExpression categoryEq(ClubCategoryType categoryType) {
        return categoryType == null ? null : club.clubCategoryType.eq(categoryType);
    }

}

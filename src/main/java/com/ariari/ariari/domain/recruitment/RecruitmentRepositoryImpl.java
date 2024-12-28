package com.ariari.ariari.domain.recruitment;

import com.ariari.ariari.domain.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
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

import java.time.LocalDateTime;
import java.util.List;

import static com.ariari.ariari.domain.club.QClub.*;
import static com.ariari.ariari.domain.recruitment.QRecruitment.recruitment;

@RequiredArgsConstructor
public class RecruitmentRepositoryImpl implements RecruitmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final int SIZE_OF_RECRUITMENT_RANKING_LIST = 9;

    @Override
    public Page<Recruitment> searchRecruitmentPage(School school, ClubSearchCondition condition, Pageable pageable) {
        JPAQuery<Recruitment> query = queryFactory
                .selectFrom(recruitment)
                .join(recruitment.club, club)
                .where(isActivated(),
                        schoolIsNull()
                                .or(schoolEq(school)),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(recruitment.getType(), recruitment.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<Recruitment> content= query.fetch();

        Long total = queryFactory
                .select(recruitment.count())
                .from(recruitment)
                .join(recruitment.club, club)
                .where(isActivated(),
                        schoolIsNull()
                                .or(schoolEq(school)),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Recruitment> searchExternalPage(ClubSearchCondition condition, Pageable pageable) {
        JPAQuery<Recruitment> query = queryFactory
                .selectFrom(recruitment)
                .join(recruitment.club, club)
                .where(isActivated(),
                        schoolIsNull(),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(recruitment.getType(), recruitment.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<Recruitment> content= query.fetch();

        Long total = queryFactory
                .select(recruitment.count())
                .from(recruitment)
                .join(recruitment.club, club)
                .where(isActivated(),
                        schoolIsNull(),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Recruitment> searchInternalPage(School school, ClubSearchCondition condition, Pageable pageable) {
        JPAQuery<Recruitment> query = queryFactory
                .selectFrom(recruitment)
                .join(recruitment.club, club)
                .where(isActivated(),
                        schoolEq(school),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(recruitment.getType(), recruitment.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
        List<Recruitment> content= query.fetch();

        Long total = queryFactory
                .select(recruitment.count())
                .from(recruitment)
                .join(recruitment.club, club)
                .where(isActivated(),
                        schoolEq(school),
                        categoryIn(condition.getClubCategoryTypes()),
                        regionIn(condition.getClubRegionTypes()),
                        participantIn(condition.getParticipantTypes()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<Recruitment> findExternalRankingList() {
        return queryFactory
                .selectFrom(recruitment)
                .where(isActivated(),
                        schoolIsNull())
                .orderBy(recruitment.views.desc())
                .limit(SIZE_OF_RECRUITMENT_RANKING_LIST)
                .fetch();
    }

    @Override
    public List<Recruitment> findInternalRankingList(School school) {
        return queryFactory
                .selectFrom(recruitment)
                .where(isActivated(),
                        schoolEq(school))
                .orderBy(recruitment.views.desc())
                .limit(SIZE_OF_RECRUITMENT_RANKING_LIST)
                .fetch();
    }

    private BooleanExpression schoolEq(School school) {
        return school == null ? null : club.school.eq(school);
    }

    private BooleanExpression schoolIsNull() {
        return club.school.isNull();
    }

    private BooleanExpression isActivated() {
        return recruitment.isActivated.eq(true)
                .and(recruitment.endDateTime.before(LocalDateTime.now()));
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

}

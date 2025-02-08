package com.ariari.ariari.domain.recruitment.recruitment;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.dto.req.ClubSearchCondition;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.club.enums.ParticipantType;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.school.School;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static com.ariari.ariari.domain.club.QClub.club;
import static com.ariari.ariari.domain.recruitment.QRecruitment.recruitment;

@Slf4j
@RequiredArgsConstructor
public class RecruitmentRepositoryImpl implements RecruitmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final int SIZE_OF_RECRUITMENT_RANKING_LIST = 9;

    @Override
    public Page<Recruitment> searchRecruitmentPage(School school, ClubSearchCondition condition, Pageable pageable) {
        JPAQuery<Recruitment> query = queryFactory
                .selectFrom(recruitment)
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

    @Override
    public boolean existsDuplicatePeriodRecruitment(Club club, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return !queryFactory
                .selectFrom(recruitment)
                .where(clubEq(club),
                        isActivated(),
                        periodOverlapped(startDateTime, endDateTime))
                .fetch().isEmpty();
    }

    private BooleanExpression schoolEq(School school) {
        return school == null ? null : recruitment.club.school.eq(school);
    }

    private BooleanExpression schoolIsNull() {
        return recruitment.club.school.isNull();
    }

    private BooleanExpression isActivated() {
        return recruitment.isActivated.eq(true)
                .and(recruitment.endDateTime.after(LocalDateTime.now()));
    }

    private BooleanExpression categoryIn(List<ClubCategoryType> categoryTypes) {
        return categoryTypes.isEmpty() ? null : recruitment.club.clubCategoryType.in(categoryTypes);
    }

    private BooleanExpression regionIn(List<ClubRegionType> regionTypes) {
        return regionTypes.isEmpty() ? null : recruitment.club.clubRegionType.in(regionTypes);
    }

    private BooleanExpression participantIn(List<ParticipantType> participantTypes) {
        return participantTypes.isEmpty() ? null : recruitment.club.participantType.in(participantTypes);
    }

    private BooleanExpression clubEq(Club clubParam) {
        return club.eq(clubParam);
    }

    private BooleanExpression periodOverlapped(LocalDateTime start, LocalDateTime end) {
        return recruitment.startDateTime.between(start, end)
                .or(recruitment.endDateTime.between(start, end))
                .or(recruitment.startDateTime.before(start)
                        .and(recruitment.endDateTime.after(end)));
    }

}

package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInClubSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.req.MyAppliesSearchType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.ariari.ariari.domain.recruitment.apply.QApply.*;
import static com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType.*;


@RequiredArgsConstructor
public class ApplyRepositoryImpl implements ApplyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Apply> searchApplyByClub(Club club, AppliesInClubSearchCondition condition, Pageable pageable) {
        List<Apply> content = queryFactory.select(apply)
                .from(apply)
                .where(apply.recruitment.club.eq(club),
                        isPendent(condition.getIsPendent()),
                        nicknameContains(condition.getQuery()),
                        betweenDateTime(condition.getStartDateTime(), condition.getEndDateTime()))
                .orderBy(apply.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(apply.count())
                .from(apply)
                .where(apply.recruitment.club.eq(club),
                        isPendent(condition.getIsPendent()),
                        nicknameContains(condition.getQuery()),
                        betweenDateTime(condition.getStartDateTime(), condition.getEndDateTime()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Apply> searchByMember(Member member, MyAppliesSearchType searchType, Pageable pageable) {
        List<Apply> content = queryFactory.selectFrom(apply)
                .where(memberEq(member),
                        searchMyCond(searchType))
                .orderBy(apply.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(apply.count())
                .from(apply)
                .where(memberEq(member),
                        searchMyCond(searchType))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression isPendent(Boolean isPendent) {
        if (!isPendent) {
            return null;
        }
        return apply.applyStatusType.eq(PENDENCY);
    }

    private BooleanExpression nicknameContains(String query) {
        return query != null ? apply.member.nickName.contains(query) : null;
    }

    private BooleanExpression betweenDateTime(LocalDateTime start, LocalDateTime end) {
        if (start == null) {
            return null;
        }
        return apply.createdDateTime.between(start, end);
    }

    private BooleanExpression memberEq(Member member) {
        return apply.member.eq(member);
    }

    private BooleanExpression searchMyCond(MyAppliesSearchType searchType) {
        if (searchType.equals(MyAppliesSearchType.IN_PROGRESS)) {
            return apply.applyStatusType.in(PENDENCY, INTERVIEW);
        } else if (searchType.equals(MyAppliesSearchType.FINALIZED)) {
            return apply.applyStatusType.in(APPROVE, REFUSAL);
        } else {
            return null;
        }
    }

}

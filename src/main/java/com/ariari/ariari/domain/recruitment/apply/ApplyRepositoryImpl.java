package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInTeamSearchCondition;
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
    public Page<Apply> searchApplyByClub(Club club, AppliesInTeamSearchCondition condition, Pageable pageable) {
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
    public Page<Apply> findFinalizedAppliesByMember(Member member, Pageable pageable) {
        List<Apply> content = queryFactory.selectFrom(apply)
                .where(apply.member.eq(member),
                        apply.applyStatusType.in(APPROVE, REFUSAL))
                .orderBy(apply.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(apply.count())
                .from(apply)
                .where(apply.member.eq(member),
                        apply.applyStatusType.in(APPROVE, REFUSAL))
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

}

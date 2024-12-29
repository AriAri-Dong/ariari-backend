package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.QClub;
import com.ariari.ariari.domain.recruitment.apply.dto.req.ApplySearchCondition;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import static com.ariari.ariari.domain.recruitment.apply.QApply.*;


@RequiredArgsConstructor
public class ApplyRepositoryImpl implements ApplyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Apply> searchApplyByClub(Club club, ApplySearchCondition condition) {
        JPAQuery<Apply> query = queryFactory.select(apply)
                .from(apply)
                .where(apply.recruitment.club.eq(club),
                        isPendent(condition.getIsPendent()));

        return null;
    }

    private BooleanExpression isPendent(Boolean isPendent) {
        if (!isPendent) {
            return null;
        }
        return apply.applyStatusType.eq(ApplyStatusType.PENDENCY);
    }

    private BooleanExpression nicknameContains(String query) {
        return query != null ? apply.member.nickName.contains(query) : null;
    }

    private BooleanExpression nicknameContains(String query) {
        return query != null ? apply.member.nickName.contains(query) : null;
    }

}

package com.ariari.ariari.domain.club.financial;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.QClub;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.ariari.ariari.domain.club.QClub.club;
import static com.ariari.ariari.domain.club.financial.QFinancialRecord.financialRecord;


@RequiredArgsConstructor
public class FinancialRecordRepositoryImpl implements FinancialRecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findTotalByClub(Club clubParam) {
        Long total = queryFactory.select(financialRecord.amount.sum())
                .from(financialRecord)
                .join(financialRecord.club, club).on(club.eq(clubParam))
                .fetchOne();

        return total == null ? 0 : total;
    }

    @Override
    public Long findTotalByClubBefore(Club clubParam, LocalDateTime before) {
        Long total = queryFactory.select(financialRecord.amount.sum())
                .from(financialRecord)
                .join(financialRecord.club, club).on(club.eq(clubParam))
                .where(financialRecord.recordDateTime.before(before))
                .fetchOne();

        return total == null ? 0 : total;
    }

}

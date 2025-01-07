package com.ariari.ariari.domain.club.financial;

import com.ariari.ariari.domain.club.Club;

import java.time.LocalDateTime;

public interface FinancialRecordRepositoryCustom {

    Long findTotalByClub(Club club);

    Long findTotalByClubBefore(Club club, LocalDateTime before);

}

package com.ariari.ariari.domain.club.financial;

import com.ariari.ariari.domain.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>, FinancialRecordRepositoryCustom {

    Page<FinancialRecord> findByClubOrderByRecordDateTimeDesc(Club club, Pageable pageable);

}

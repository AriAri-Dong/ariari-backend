package com.ariari.ariari.domain.club.financial.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.financial.FinancialRecord;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FinancialRecordSaveReq {

    private Long amount;
    private String body;
    private LocalDateTime recordDateTime;

    public FinancialRecord toEntity(Club club) {
        return new FinancialRecord(
                this.amount,
                this.body,
                this.recordDateTime,
                club
        );
    }

    public void modifyEntity(FinancialRecord financialRecord) {
        financialRecord.modify(
                this.amount,
                this.body,
                this.recordDateTime
        );
    }

}

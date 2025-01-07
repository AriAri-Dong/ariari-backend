package com.ariari.ariari.domain.club.financial.dto;

import com.ariari.ariari.domain.club.financial.FinancialRecord;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FinancialRecordData {

    private Long id;
    private Long amount;
    private String body;
    private LocalDateTime recordDateTime;
    private LocalDateTime createdDateTime;

    private Long balance;

    public static FinancialRecordData fromEntity(FinancialRecord financialRecord) {
        return new FinancialRecordData(
                financialRecord.getId(),
                financialRecord.getAmount(),
                financialRecord.getBody(),
                financialRecord.getRecordDateTime(),
                financialRecord.getCreatedDateTime()
        );
    }

    public FinancialRecordData(Long id, Long amount, String body, LocalDateTime recordDateTime, LocalDateTime createdDateTime) {
        this.id = id;
        this.amount = amount;
        this.body = body;
        this.recordDateTime = recordDateTime;
        this.createdDateTime = createdDateTime;
    }

}

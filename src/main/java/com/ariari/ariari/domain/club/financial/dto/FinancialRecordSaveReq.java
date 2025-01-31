package com.ariari.ariari.domain.club.financial.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.financial.FinancialRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "동아리 회계 등록 형식")
public class FinancialRecordSaveReq {

    @Schema(description = "동아리 회계 액수", example = "-30000")
    private Long amount;
    @Schema(description = "동아리 회계 내용", example = "회의실 대여 비용")
    private String body;
    @Schema(description = "동아리 회계 기록 날짜/시간", example = "2025-01-15T09:08:18.467Z")
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

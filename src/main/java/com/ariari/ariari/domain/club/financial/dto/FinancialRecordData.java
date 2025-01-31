package com.ariari.ariari.domain.club.financial.dto;

import com.ariari.ariari.domain.club.financial.FinancialRecord;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "동아리 회계 데이터")
public class FinancialRecordData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 회계 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 회계 액수", example = "-30000")
    private Long amount;
    @Schema(description = "동아리 회계 내용", example = "회의실 대여 비용")
    private String body;
    @Schema(description = "동아리 회계 기록 날짜/시간", example = "2025-01-15T09:08:18.467Z")
    private LocalDateTime recordDateTime;
    @Schema(description = "동아리 회계 생성 날짜/시간", example = "2025-01-31T09:08:18.467Z")
    private LocalDateTime createdDateTime;

    @Schema(description = "동아리 회계 잔액", example = "80000")
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

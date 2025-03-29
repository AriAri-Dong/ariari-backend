package com.ariari.ariari.commons.entity.report.dto;

import com.ariari.ariari.commons.enums.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 신고 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportReq {


    @Schema(description = "신고 유형", example = "SPAM_ADVERTISEMENT")
    @NotNull(message = "신고 유형은 필수입니다.")
    private ReportType reportType;

    @Schema(description = "신고 내용")
    @Size(max = 500)
    private String body;

    @Schema(description = "신고당한 객체 ID", example = "681905476703029787")
    @NotNull(message = "신고당한 객체 ID은 필수입니다.")
    // long은 기본값이 0이라 @NotNull이 적용되지 않으므로 Long 객체 타입으로 변경
    private Long reportedEntityId;


}

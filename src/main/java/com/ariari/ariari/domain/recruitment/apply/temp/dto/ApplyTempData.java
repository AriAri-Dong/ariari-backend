package com.ariari.ariari.domain.recruitment.apply.temp.dto;

import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "임시 지원서 데이터")
public class ApplyTempData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "임시 지원서 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "임시 지원서 이름 (지원 합격 후 동아리 회원 활동명으로 사용할 이름)", example = "아리아리 원순재")
    private String name;
    @Schema(description = "임시 지원서 생성 날짜/시간", example = "2025-03-15T09:08:18.467Z")
    private LocalDateTime createdDateTime;

    @Schema(description = "임시 지원한 모집의 제목", example = "아리아리 3기 모집")
    private String recruitmentTitle;
    @Schema(description = "임시 지원한 모집이 속한 동아리 이름", example = "아리아리")
    private String clubName;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "임시 지원 모집 id", example = "673012345142938986")
    private Long recruitmentId;

    public static ApplyTempData fromEntity(ApplyTemp applyTemp) {
        return new ApplyTempData(
                applyTemp.getId(),
                applyTemp.getName(),
                applyTemp.getCreatedDateTime(),
                applyTemp.getRecruitment().getTitle(),
                applyTemp.getRecruitment().getClub().getName(),
                applyTemp.getRecruitment().getId()
        );
    }

    public static List<ApplyTempData> fromEntities(List<ApplyTemp> applyTemps) {
        return applyTemps.stream().map(ApplyTempData::fromEntity).toList();
    }

}

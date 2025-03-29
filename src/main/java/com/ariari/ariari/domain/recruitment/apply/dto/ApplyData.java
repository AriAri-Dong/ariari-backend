package com.ariari.ariari.domain.recruitment.apply.dto;

import com.ariari.ariari.domain.member.member.dto.MemberData;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "지원서 데이터 (memberData 는 작성 회원 데이터입니다. ")
public class ApplyData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "지원서 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "지원서 이름 (지원 합격 후 동아리 회원 활동명으로 사용할 이름)", example = "아리아리 원순재")
    private String name;
    @Schema(description = "지원서 상태 타입", example = "APPROVE")
    private ApplyStatusType applyStatusType;
    @Schema(description = "지원서 생성 날짜/시간", example = "2025-03-15T09:08:18.467Z")
    private LocalDateTime createdDateTime;

    private MemberData memberData;
    @Schema(description = "지원한 모집의 제목", example = "아리아리 3기 모집")
    private String recruitmentTitle;
    @Schema(description = "지원한 모집이 속한 동아리 이름", example = "아리아리")
    private String clubName;

    public static ApplyData fromEntity(Apply apply) {
        return new ApplyData(
                apply.getId(),
                apply.getName(),
                apply.getApplyStatusType(),
                apply.getCreatedDateTime(),
                MemberData.fromEntity(apply.getMember()),
                apply.getRecruitment().getTitle(),
                apply.getRecruitment().getClub().getName()
        );
    }

    public static List<ApplyData> fromEntities(List<Apply> applies) {
        return applies.stream().map(ApplyData::fromEntity).toList();
    }

}

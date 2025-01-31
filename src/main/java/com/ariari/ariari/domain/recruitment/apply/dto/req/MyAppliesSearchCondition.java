package com.ariari.ariari.domain.recruitment.apply.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "내 지원서 검색 조건 (IN_PROGRESS : 진행 중(PENDENCY, INTERVIEW), FINALIZE : 결과 발표(APPROVE, REFUSAL))")
public enum MyAppliesSearchCondition {

    IN_PROGRESS,
    FINALIZED

}

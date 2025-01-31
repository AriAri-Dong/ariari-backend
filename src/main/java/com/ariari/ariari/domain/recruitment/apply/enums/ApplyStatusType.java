package com.ariari.ariari.domain.recruitment.apply.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "지원 상태 타입 (PENDENCY : 대기, INTERVIEW : 면접 대기, APPROVE : 승인, REFUSAL : 거절)")
public enum ApplyStatusType {

    PENDENCY,
    APPROVE,
    REFUSAL,
    INTERVIEW

}

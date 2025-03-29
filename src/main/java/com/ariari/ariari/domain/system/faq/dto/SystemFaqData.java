package com.ariari.ariari.domain.system.faq.dto;

import com.ariari.ariari.domain.system.faq.SystemFaq;
import com.ariari.ariari.domain.system.faq.enums.SystemFaqStatusType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "서비스 FAQ 데이터 ")
@Getter
public class SystemFaqData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "서비스 FAQ id", example = "673012345142938986")
    private final Long id;
    @Schema(description = "서비스 FAQ 제목", example = "아리아리에서 개발한 서비스의 배포 시작 방법")
    private final String title;
    @Schema(description = "서비스 FAQ 내용", example = "main 브렌치에 push!")
    private final String body;
    @Schema(description = "서비스 FAQ 타입", example = "APPROVE")
    private final SystemFaqStatusType systemFaqStatusType;

    private SystemFaqData(Long id, String title, String body, SystemFaqStatusType systemFaqStatusType) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.systemFaqStatusType = systemFaqStatusType;
    }

    public static SystemFaqData fromEntity(SystemFaq systemFaq){
        return new SystemFaqData(systemFaq.getId(),
                systemFaq.getTitle(),
                systemFaq.getBody(),
                systemFaq.getSystemFaqStatusType());
    }
}

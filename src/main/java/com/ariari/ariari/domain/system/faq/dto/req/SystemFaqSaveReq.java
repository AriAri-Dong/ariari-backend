package com.ariari.ariari.domain.system.faq.dto.req;

import com.ariari.ariari.domain.system.faq.SystemFaq;
import com.ariari.ariari.domain.system.faq.enums.SystemFaqStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "서비스 FAQ 저장 형식")
@Getter
public class SystemFaqSaveReq {

    @Schema(description = "서비스 FAQ 제목 (질문)", example = "아리아리에 가입하기 위해 필요한 서류는 무엇인가요?")
    private String title;
    @Schema(description = "서비스 FAQ 답변", example = "아리아리에 가입하기 위해서는 포트폴리오와 깃허브 URI를 제출해야 합니다.")
    private String body;
    @Schema(description = "서비스 FAQ 타입", example = "일정")
    private SystemFaqStatusType systemFaqStatusType;


    public SystemFaq toEntity() {
        return SystemFaq.create(title, body, systemFaqStatusType);
    }

}

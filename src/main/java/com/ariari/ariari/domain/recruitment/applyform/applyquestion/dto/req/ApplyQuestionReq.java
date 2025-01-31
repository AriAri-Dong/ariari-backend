package com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.req;

import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "지원 형식 질문 등록 형식")
public class ApplyQuestionReq {

    @Schema(description = "질문 내용", example = "프로젝트 경험이 있나요?")
    private String body;

    public ApplyQuestion toEntity() {
        return new ApplyQuestion(body);
    }

}

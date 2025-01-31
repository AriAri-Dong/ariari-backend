package com.ariari.ariari.domain.recruitment.apply.answer.dto.req;

import com.ariari.ariari.commons.serializer.StringToLongDeserializer;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Schema(description = "지원서 응답 형식")
public class ApplyAnswerReq {

    @JsonDeserialize(using = StringToLongDeserializer.class)
    @Schema(description = "지원 형식 질문 id", example = "673012345142938986")
    private Long applyQuestionId;
    @Schema(description = "질문에 대한 응답 내용", example = "네. 5번의 프로젝트 경험이 있습니다.")
    private String body;

    public ApplyAnswer toEntity(Map<Long, ApplyQuestion> applyQuestionMap) {
        return new ApplyAnswer(
                this.body,
                applyQuestionMap.get(applyQuestionId)
        );
    }

}

package com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.req;

import com.ariari.ariari.commons.serializer.StringToLongDeserializer;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "임시 지원서 응답 수정 형식")
public class ApplyAnswerTempModifyReq {

    @JsonDeserialize(using = StringToLongDeserializer.class)
    @Schema(description = "지원 형식 질문 id", example = "673012345142938986")
    private Long applyQuestionId;
    @Schema(description = "질문에 대한 응답 내용", example = "아니요. 프로젝트 경험이 아직 없습니다.")
    private String body;

    public void modifyEntity(Map<Long, ApplyAnswerTemp> modifyMap) {
        ApplyAnswerTemp answerTemp = modifyMap.get(applyQuestionId);
        answerTemp.setBody(body);
    }

}

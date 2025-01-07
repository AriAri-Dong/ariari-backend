package com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.req;

import com.ariari.ariari.commons.serializer.StringToLongDeserializer;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Map;

@Data
public class ApplyAnswerTempSaveReq {

    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long applyQuestionId;
    private String body;

    public ApplyAnswerTemp toEntity(Map<Long, ApplyQuestion> applyQuestionMap) {
        return new ApplyAnswerTemp(
                this.body,
                applyQuestionMap.get(applyQuestionId)
        );
    }

}

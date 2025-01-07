package com.ariari.ariari.domain.recruitment.apply.answer.dto.req;

import com.ariari.ariari.commons.serializer.StringToLongDeserializer;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApplyAnswerReq {

    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long applyQuestionId;
    private String body;

    public ApplyAnswer toEntity(Map<Long, ApplyQuestion> applyQuestionMap) {
        return new ApplyAnswer(
                this.body,
                applyQuestionMap.get(applyQuestionId)
        );
    }

}

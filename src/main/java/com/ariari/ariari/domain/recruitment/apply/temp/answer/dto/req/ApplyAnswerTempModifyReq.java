package com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.req;

import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import lombok.Data;

import java.util.Map;

@Data
public class ApplyAnswerTempModifyReq {

    private Long applyQuestionId;
    private String body;

    public void modifyEntity(Map<Long, ApplyAnswerTemp> modifyMap) {
        ApplyAnswerTemp answerTemp = modifyMap.get(applyQuestionId);
        answerTemp.setBody(body);
    }

}

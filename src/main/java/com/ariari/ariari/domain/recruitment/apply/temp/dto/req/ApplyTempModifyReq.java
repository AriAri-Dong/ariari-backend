package com.ariari.ariari.domain.recruitment.apply.temp.dto.req;

import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.req.ApplyAnswerTempModifyReq;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApplyTempModifyReq {

    private String name;
    private String portfolioUrl;
    private List<ApplyAnswerTempModifyReq> applyAnswerTemp = new ArrayList<>();

    public void modifyEntity(ApplyTemp applyTemp) {
        applyTemp.modify(name, portfolioUrl);

        Map<Long, ApplyAnswerTemp> modifyMap = getModifyMap(applyTemp);
        for (ApplyAnswerTempModifyReq answerModifyReq : applyAnswerTemp) {
            answerModifyReq.modifyEntity(modifyMap);
        }
    }

    private Map<Long, ApplyAnswerTemp> getModifyMap(ApplyTemp applyTemp) {
        Map<Long, ApplyAnswerTemp> modifyMap = new HashMap<>();

        for (ApplyAnswerTemp answerTemp : applyTemp.getApplyAnswerTemps()) {
            ApplyQuestion question = answerTemp.getApplyQuestion();
            modifyMap.put(question.getId(), answerTemp);
        }
        return modifyMap;
    }

}

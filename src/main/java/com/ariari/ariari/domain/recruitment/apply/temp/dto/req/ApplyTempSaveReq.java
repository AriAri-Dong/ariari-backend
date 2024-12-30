package com.ariari.ariari.domain.recruitment.apply.temp.dto.req;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.req.ApplyAnswerTempSaveReq;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApplyTempSaveReq {

    private String name;
    private String portfolioUrl;
    private List<ApplyAnswerTempSaveReq> applyAnswerTemp = new ArrayList<>();

    public ApplyTemp toEntity(Member member, Recruitment recruitment) {
        Map<Long, ApplyQuestion> applyQuestionMap = recruitment.getApplyForm().getApplyQuestionMap();
        List<ApplyAnswerTemp> applyAnswerTemps = applyAnswerTemp.stream().map(aa -> aa.toEntity(applyQuestionMap)).toList();

        ApplyTemp applyTemp = new ApplyTemp(
                this.name,
                portfolioUrl,
                member,
                recruitment,
                applyAnswerTemps
        );

        for (ApplyAnswerTemp answerTemp : applyAnswerTemps) {
            answerTemp.setApplyTemp(applyTemp);
        }

        return applyTemp;
    }

}

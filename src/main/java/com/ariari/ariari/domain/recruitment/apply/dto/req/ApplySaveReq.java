package com.ariari.ariari.domain.recruitment.apply.dto.req;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.apply.answer.dto.req.ApplyAnswerReq;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApplySaveReq {

    private String name;
    private String portfolioUrl;
    private List<ApplyAnswerReq> applyAnswers = new ArrayList<>();

    public Apply toEntity(Member member, Recruitment recruitment) {
        Map<Long, ApplyQuestion> applyQuestionMap = recruitment.getApplyForm().getApplyQuestionMap();
        List<ApplyAnswer> applyAnswerList = applyAnswers.stream().map(aa -> aa.toEntity(applyQuestionMap)).toList();

        Apply apply = new Apply(
                this.name,
                portfolioUrl,
                member,
                recruitment,
                applyAnswerList
        );

        for (ApplyAnswer applyAnswer : applyAnswerList) {
            applyAnswer.setApply(apply);
        }

        return apply;
    }

}

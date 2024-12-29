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

    private List<ApplyAnswerReq> applyAnswers = new ArrayList<>();

    public Apply toEntity(Member member, Recruitment recruitment) {
        Map<Long, ApplyQuestion> applyQuestionMap = getApplyQuestionMap(recruitment);
        List<ApplyAnswer> applyAnswerList = applyAnswers.stream().map(aa -> aa.toEntity(applyQuestionMap)).toList();

        Apply apply = new Apply(
                member,
                recruitment,
                applyAnswerList
        );

        for (ApplyAnswer applyAnswer : applyAnswerList) {
            applyAnswer.setApply(apply);
        }

        return apply;
    }

    private Map<Long, ApplyQuestion> getApplyQuestionMap(Recruitment recruitment) {
        List<ApplyQuestion> applyQuestions = recruitment.getApplyForm().getApplyQuestions();

        HashMap<Long, ApplyQuestion> applyQuestionMap = new HashMap<>();
        for (ApplyQuestion applyQuestion : applyQuestions) {
            applyQuestionMap.put(applyQuestion.getId(), applyQuestion);
        }

        return applyQuestionMap;
    }

}

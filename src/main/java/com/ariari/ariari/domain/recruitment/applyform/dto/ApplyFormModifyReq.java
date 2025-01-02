package com.ariari.ariari.domain.recruitment.applyform.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplyFormModifyReq {

    private List<String> applyQuestionList = new ArrayList<>();

    public ApplyForm toEntity(Club club) {
        List<ApplyQuestion> applyQuestions = this.applyQuestionList.stream().map(ApplyQuestion::new).toList();

        ApplyForm applyForm = new ApplyForm(
                club,
                applyQuestions
        );

        for (ApplyQuestion applyQuestion : applyQuestions) {
            applyQuestion.setApplyForm(applyForm);
        }

        return applyForm;
    }

}

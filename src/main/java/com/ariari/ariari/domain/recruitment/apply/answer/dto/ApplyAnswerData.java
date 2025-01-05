package com.ariari.ariari.domain.recruitment.apply.answer.dto;

import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyAnswerData {

    private String body;
    private ApplyQuestionData applyQuestionData;

    public static ApplyAnswerData fromEntity(ApplyAnswer applyAnswer) {
        return new ApplyAnswerData(
                applyAnswer.getBody(),
                ApplyQuestionData.fromEntity(applyAnswer.getApplyQuestion())
        );
    }

    public static List<ApplyAnswerData> fromEntities(List<ApplyAnswer> applyAnswers) {
        return applyAnswers.stream().map(ApplyAnswerData::fromEntity).toList();
    }

}

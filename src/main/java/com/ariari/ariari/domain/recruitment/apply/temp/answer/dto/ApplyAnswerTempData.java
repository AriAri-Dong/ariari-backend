package com.ariari.ariari.domain.recruitment.apply.temp.answer.dto;

import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyAnswerTempData {

    private String body;
    private ApplyQuestionData applyQuestionData;

    public static ApplyAnswerTempData fromEntity(ApplyAnswerTemp applyAnswerTemp) {
        return new ApplyAnswerTempData(
                applyAnswerTemp.getBody(),
                ApplyQuestionData.fromEntity(applyAnswerTemp.getApplyQuestion())
        );
    }

    public static List<ApplyAnswerTempData> fromEntities(List<ApplyAnswerTemp> applyAnswerTemps) {
        return applyAnswerTemps.stream().map(ApplyAnswerTempData::fromEntity).toList();
    }

}

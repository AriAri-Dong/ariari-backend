package com.ariari.ariari.domain.recruitment.applyform.dto;

import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyFormData {

    private List<ApplyQuestionData> applyQuestionDataList;

    public static ApplyFormData fromEntity(ApplyForm applyForm) {
        return new ApplyFormData(
                ApplyQuestionData.fromEntities(applyForm.getApplyQuestions())
        );
    }

}

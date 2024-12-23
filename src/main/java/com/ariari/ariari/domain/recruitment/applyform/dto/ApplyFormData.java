package com.ariari.ariari.domain.recruitment.applyform.dto;

import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyFormData {

    private Long id;
    private List<ApplyQuestionData> applyQuestionDataList;

    public static ApplyFormData fromEntity(ApplyForm applyForm) {
        return new ApplyFormData(
                applyForm.getId(),
                ApplyQuestionData.fromEntities(applyForm.getApplyQuestions())
        );
    }

}

package com.ariari.ariari.domain.recruitment.applyform.dto;

import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "지원 형식 데이터")
public class ApplyFormData {

    @Schema(description = "지원 형식 질문 데이터 리스트")
    private List<ApplyQuestionData> applyQuestionDataList;

    public static ApplyFormData fromEntity(ApplyForm applyForm) {
        return new ApplyFormData(
                ApplyQuestionData.fromEntities(applyForm.getApplyQuestions())
        );
    }

}

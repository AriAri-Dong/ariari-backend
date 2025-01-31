package com.ariari.ariari.domain.recruitment.apply.temp.answer.dto;

import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "임시 지원서 응답 데이터")
public class ApplyAnswerTempData {

    @Schema(description = "질문에 대한 응답 내용", example = "네. 프로젝트에 대한 경험이 5번 있습니다.")
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

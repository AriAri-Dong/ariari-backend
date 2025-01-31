package com.ariari.ariari.domain.recruitment.apply.answer.dto;

import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "지원서 응답 데이터 (applyQuestionData 는 응답한 지원 형식 질문 데이터입니다.)")
public class ApplyAnswerData {

    @Schema(description = "질문에 대한 응답 내용", example = "네. 5번의 프로젝트 경험이 있습니다.")
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

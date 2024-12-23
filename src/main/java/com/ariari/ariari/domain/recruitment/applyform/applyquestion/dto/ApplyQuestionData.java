package com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto;

import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ApplyQuestionData {

    private Long id;
    private String body;

    public static ApplyQuestionData fromEntity(ApplyQuestion applyQuestion) {
        return ApplyQuestionData.builder()
                .id(applyQuestion.getId())
                .body(applyQuestion.getBody())
                .build();
    }

    public static List<ApplyQuestionData> fromEntities(List<ApplyQuestion> applyQuestions) {
        return applyQuestions.stream().map(ApplyQuestionData::fromEntity).collect(Collectors.toList());
    }

}

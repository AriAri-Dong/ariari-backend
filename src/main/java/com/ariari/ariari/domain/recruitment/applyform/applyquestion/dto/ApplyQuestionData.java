package com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto;

import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Schema(description = "지원 형식 질문 데이터")
public class ApplyQuestionData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "지원 질문 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "지원 질문 내용", example = "673012345142938986")
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

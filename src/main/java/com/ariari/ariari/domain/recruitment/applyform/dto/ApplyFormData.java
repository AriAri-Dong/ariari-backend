package com.ariari.ariari.domain.recruitment.applyform.dto;

import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.ApplyQuestionData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원 형식 데이터")
public class ApplyFormData {

    @Schema(description = "특별 질문 리스트")
    private SpecialQuestionList specialQuestionList;

    @Schema(description = "지원 형식 질문 데이터 리스트")
    private List<ApplyQuestionData> applyQuestionDataList;

    private Boolean portfolio;

    public static ApplyFormData fromEntity(ApplyForm applyForm) {
        ApplyFormData applyFormData = new ApplyFormData();
        Map<String, Long> map = applyForm.getApplyQuestionBodyMap(); // body : id(pk)

        SpecialQuestionList specialQuestionList = new SpecialQuestionList();

        List<ApplyQuestion> applyQuestions = applyForm.getApplyQuestions();
        Set<String> questions1 = applyQuestions.stream().map(ApplyQuestion::getBody).collect(Collectors.toSet());
        Set<String> questions2 = applyQuestions.stream().map(ApplyQuestion::getBody).collect(Collectors.toSet());
        for (String question : questions1) {
            try {
                Field field = SpecialQuestionList.class.getDeclaredField(question);
                field.setAccessible(true);
                field.set(specialQuestionList, map.get(question));
                questions2.remove(question);
            } catch (Exception ignored) {}
        }

        applyFormData.setSpecialQuestionList(specialQuestionList);
        applyFormData.setApplyQuestionDataList(questions2.stream().map(q -> new ApplyQuestionData(map.get(q), q)).toList());
        applyFormData.setPortfolio(applyForm.getRequiresPortfolio());
        return applyFormData;
    }

}

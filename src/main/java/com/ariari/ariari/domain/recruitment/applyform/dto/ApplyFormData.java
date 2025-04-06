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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long gender;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long birthday;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long phone;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long email;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long education;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long major;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long occupation;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long mbti;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long availablePeriod;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preferredActivityField;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long hobby;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sns;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long motivation;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long activityExperience;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long skill;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long aspiration;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long availableTime;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long referralSource;

    @Schema(description = "지원 형식 질문 데이터 리스트")
    private List<ApplyQuestionData> applyQuestionDataList;

    public static ApplyFormData fromEntity(ApplyForm applyForm) {
        ApplyFormData applyFormData = new ApplyFormData();
        Map<String, Long> map = applyForm.getApplyQuestionBodyMap(); // body : id(pk)

        List<ApplyQuestion> applyQuestions = applyForm.getApplyQuestions();
        Set<String> questions1 = applyQuestions.stream().map(ApplyQuestion::getBody).collect(Collectors.toSet());
        Set<String> questions2 = applyQuestions.stream().map(ApplyQuestion::getBody).collect(Collectors.toSet());
        for (String question : questions1) {
            try {
                Field field = ApplyFormData.class.getDeclaredField(question);
                field.setAccessible(true);
                field.set(applyFormData, map.get(question));
                questions2.remove(question);
            } catch (Exception ignored) {}
        }

        applyFormData.setApplyQuestionDataList(questions2.stream().map(q -> new ApplyQuestionData(map.get(q), q)).toList());

        return applyFormData;
    }

}

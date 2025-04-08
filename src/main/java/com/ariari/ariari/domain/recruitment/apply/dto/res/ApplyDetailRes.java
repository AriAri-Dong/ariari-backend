package com.ariari.ariari.domain.recruitment.apply.dto.res;

import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.apply.answer.dto.ApplyAnswerData;
import com.ariari.ariari.domain.recruitment.apply.dto.ApplyData;
import com.ariari.ariari.domain.recruitment.apply.dto.SpecialAnswerList;
import com.ariari.ariari.domain.recruitment.applyform.dto.SpecialQuestionList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "지원서 상세 응답")
public class ApplyDetailRes {

    private ApplyData applyData;
    @Schema(description = "지원 형식 질문에 대한 응답 데이터 리스트")
    private List<ApplyAnswerData> applyAnswerDataList;

    @Schema(description = "포트폴리오 파일 URI")
    private String fileUri;
    @Schema(description = "포트폴리오 URI")
    private String portfolioUrl;

    private SpecialAnswerList specialAnswerList;

    public static ApplyDetailRes fromEntity(Apply apply) {
        List<ApplyAnswer> answerList =  new ArrayList<>();
        SpecialAnswerList specialAnswerList = new SpecialAnswerList();

        for (ApplyAnswer answer : apply.getApplyAnswers()) {
            try {
                Field field = SpecialAnswerList.class.getDeclaredField(answer.getApplyQuestion().getBody());
                field.setAccessible(true);
                field.set(specialAnswerList, answer.getBody());
            } catch (NoSuchFieldException e) {
                answerList.add(answer);
            } catch (IllegalAccessException ignored) {}
        }

        return new ApplyDetailRes(
                ApplyData.fromEntity(apply),
                ApplyAnswerData.fromEntities(answerList),
                apply.getFileUri(),
                apply.getPortfolioUrl(),
                specialAnswerList
        );

    }

}

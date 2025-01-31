package com.ariari.ariari.domain.recruitment.apply.temp.dto.req;

import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.req.ApplyAnswerTempModifyReq;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "임시 지원서 수정 형식")
public class ApplyTempModifyReq {

    @Schema(description = "임시 지원서 이름 (지원 합격 후 동아리 회원 활동명으로 사용할 이름)", example = "아리아 원순재")
    private String name;
    @Schema(description = "임시 지원서 포트폴리오 URI", example = "notion.so/asdfjewiwk-3435dkfklasdf")
    private String portfolioUrl;
    @Schema(description = "임시 지원서 응답 수정 리스트")
    private List<ApplyAnswerTempModifyReq> applyAnswerTemp = new ArrayList<>();

    public void modifyEntity(ApplyTemp applyTemp) {
        applyTemp.modify(name, portfolioUrl);

        Map<Long, ApplyAnswerTemp> modifyMap = getModifyMap(applyTemp);
        for (ApplyAnswerTempModifyReq answerModifyReq : applyAnswerTemp) {
            answerModifyReq.modifyEntity(modifyMap);
        }
    }

    private Map<Long, ApplyAnswerTemp> getModifyMap(ApplyTemp applyTemp) {
        Map<Long, ApplyAnswerTemp> modifyMap = new HashMap<>();

        for (ApplyAnswerTemp answerTemp : applyTemp.getApplyAnswerTemps()) {
            ApplyQuestion question = answerTemp.getApplyQuestion();
            modifyMap.put(question.getId(), answerTemp);
        }
        return modifyMap;
    }

}

package com.ariari.ariari.domain.recruitment.applyform.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.dto.req.ApplyQuestionReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "지원 형식 수정 형식")
public class ApplyFormModifyReq {

    @Schema(description = "지원 형식 질문 리스트")
    private List<ApplyQuestionReq> applyQuestionReqList = new ArrayList<>();

    public ApplyForm toEntity(Club club) {
        List<ApplyQuestion> applyQuestions = this.applyQuestionReqList.stream().map(ApplyQuestionReq::toEntity).toList();

        ApplyForm applyForm = new ApplyForm(
                club,
                applyQuestions
        );

        for (ApplyQuestion applyQuestion : applyQuestions) {
            applyQuestion.setApplyForm(applyForm);
        }

        return applyForm;
    }

}

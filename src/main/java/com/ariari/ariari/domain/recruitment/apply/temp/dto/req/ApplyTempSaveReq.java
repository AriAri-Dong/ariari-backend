package com.ariari.ariari.domain.recruitment.apply.temp.dto.req;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.req.ApplyAnswerTempSaveReq;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "임시 지원서 등록 형식")
public class ApplyTempSaveReq {

    @Schema(description = "임시 지원서 이름 (지원 합격 후 동아리 회원 활동명으로 사용할 이름)", example = "아리아 원순재")
    private String name;
    @Schema(description = "임시 지원서 포트폴리오 URI", example = "notion.so/asdfjewiwk-3435dkfklasdf")
    private String portfolioUrl;
    @Schema(description = "임시 지원서 응답 등록 리스트")
    private List<ApplyAnswerTempSaveReq> applyAnswerTemp = new ArrayList<>();

    public ApplyTemp toEntity(Member member, Recruitment recruitment) {
        Map<Long, ApplyQuestion> applyQuestionMap = recruitment.getApplyForm().getApplyQuestionMap();
        List<ApplyAnswerTemp> applyAnswerTemps = applyAnswerTemp.stream().map(aa -> aa.toEntity(applyQuestionMap)).toList();

        ApplyTemp applyTemp = new ApplyTemp(
                this.name,
                this.portfolioUrl,
                member,
                recruitment,
                applyAnswerTemps
        );

        for (ApplyAnswerTemp answerTemp : applyAnswerTemps) {
            answerTemp.setApplyTemp(applyTemp);
        }

        return applyTemp;
    }

}

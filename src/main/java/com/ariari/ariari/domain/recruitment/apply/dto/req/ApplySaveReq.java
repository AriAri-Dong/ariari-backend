package com.ariari.ariari.domain.recruitment.apply.dto.req;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.apply.answer.dto.req.ApplyAnswerReq;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "지원서 등록 형식")
public class ApplySaveReq {

    @Schema(description = "지원서 이름 (지원 합격 후 동아리 회원 활동명으로 사용할 이름)", example = "아리아리 원순재")
    private String name;
    @Schema(description = "지원서 포트폴리오 URI", example = "notion.so/asdfjewiwk-3435dkfklasdf")
    private String portfolioUrl;
    @Schema(description = "지원서 응답 리스트")
    private List<ApplyAnswerReq> applyAnswers = new ArrayList<>();

    public Apply toEntity(Member member, Recruitment recruitment) {
        Map<Long, ApplyQuestion> applyQuestionMap = recruitment.getApplyForm().getApplyQuestionMap();
        List<ApplyAnswer> applyAnswerList = applyAnswers.stream().map(aa -> aa.toEntity(applyQuestionMap)).toList();

        Apply apply = new Apply(
                this.name,
                portfolioUrl,
                member,
                recruitment,
                applyAnswerList
        );

        for (ApplyAnswer applyAnswer : applyAnswerList) {
            applyAnswer.setApply(apply);
        }

        return apply;
    }

}

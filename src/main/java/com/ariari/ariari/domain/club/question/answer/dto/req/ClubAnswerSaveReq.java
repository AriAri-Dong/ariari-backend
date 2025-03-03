package com.ariari.ariari.domain.club.question.answer.dto.req;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "동아리 Q&A 응답 등록 형식")
public class ClubAnswerSaveReq {

    @Schema(description = "동아리 응답 내용", example = "아리아리에 제출해야 하는 서류는 포트폴리오와 Github URI입니다.")
    private String body;

    public ClubAnswer toEntity(ClubQuestion clubQuestion ) {
        return new ClubAnswer(
                body,
                clubQuestion
        );
    }

    public void modifyEntity(ClubAnswer clubAnswer) {
        clubAnswer.setBody(body);
    }

}

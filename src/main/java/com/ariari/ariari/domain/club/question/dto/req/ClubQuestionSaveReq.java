package com.ariari.ariari.domain.club.question.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "동아리 Q&A 질문 등록 형식")
public class ClubQuestionSaveReq {

    @Schema(description = "동아리 Q&A 제목", example = "아리아리 가입 방법 문의")
    private String title;
    @Schema(description = "동아리 Q&A 제목", example = "아리아리에 가입할 때 제출해야하는 서류가 어떻게 될까요?")
    private String body;

    public ClubQuestion toEntity(Club club, Member member) {
        return new ClubQuestion(
                title,
                body,
                club,
                member
        );
    }

}

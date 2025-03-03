package com.ariari.ariari.domain.club.question.answer.dto;

import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "동아리 Q&A 응답 데이터 (clubMemberData 는 작성한 동아리 회원 데이터입니다.)")
public class ClubAnswerData {

    @Schema(description = "동아리 응답 내용", example = "아리아리에 제출해야 하는 서류는 포트폴리오와 Github URI입니다.")
    private String body;

    //private ClubMemberData clubMemberData;

    public static ClubAnswerData fromEntity(ClubAnswer clubAnswer) {
        return new ClubAnswerData(
                clubAnswer.getBody()
               // ClubMemberData.fromEntity(clubAnswer.getClubMember())
        );
    }

}

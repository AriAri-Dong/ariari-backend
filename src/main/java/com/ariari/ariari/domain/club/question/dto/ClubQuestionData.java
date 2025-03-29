package com.ariari.ariari.domain.club.question.dto;

import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.answer.dto.ClubAnswerData;
import com.ariari.ariari.domain.member.member.dto.MemberData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 Q&A 질문 데이터 (memberData 는 작성한 회원 데이터입니다. clubAnswerData 는 질문에 대한 답변 데이터입니다.")
public class ClubQuestionData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 질문 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 질문 제목", example = "아리아리 가입 방법 문의")
    private String title;
    @Schema(description = "동아리 질문 제목", example = "아리아리에 가입할 때 제출해야하는 서류가 어떻게 될까요?")
    private String body;
    @Schema(description = "동아리 질문 생성 날짜/시간", example = "2025-01-31T09:08:18.467Z")
    private LocalDateTime createdDateTime;

    private MemberData memberData;
    private ClubAnswerData clubAnswerData;

    public static ClubQuestionData fromEntity(ClubQuestion clubQuestion) {
        ClubAnswerData clubAnswerData = null;
        if (clubQuestion.getClubAnswer() != null) {
            clubAnswerData = ClubAnswerData.fromEntity(clubQuestion.getClubAnswer());
        }

        MemberData memberData = null;
        if( clubQuestion.getMember() != null){
            memberData = MemberData.fromEntity(clubQuestion.getMember());
        }

        return new ClubQuestionData(
                clubQuestion.getId(),
                clubQuestion.getTitle(),
                clubQuestion.getBody(),
                clubQuestion.getCreatedDateTime(),
                memberData,
                clubAnswerData
        );
    }

    public static List<ClubQuestionData> fromEntities(List<ClubQuestion> clubQuestions) {
        return clubQuestions.stream().map(ClubQuestionData::fromEntity).toList();
    }


}

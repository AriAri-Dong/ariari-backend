package com.ariari.ariari.domain.club.question.dto;

import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.answer.dto.ClubAnswerData;
import com.ariari.ariari.domain.member.dto.MemberData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubQuestionData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String body;

    private MemberData memberData;
    private ClubAnswerData clubAnswerData;

    public static ClubQuestionData fromEntity(ClubQuestion clubQuestion) {
        ClubAnswerData clubAnswerData = null;
        if (clubQuestion.getClubAnswer() != null) {
            clubAnswerData = ClubAnswerData.fromEntity(clubQuestion.getClubAnswer());
        }

        return new ClubQuestionData(
                clubQuestion.getId(),
                clubQuestion.getTitle(),
                clubQuestion.getBody(),
                MemberData.fromEntity(clubQuestion.getMember()),
                clubAnswerData
        );
    }

    public static List<ClubQuestionData> fromEntities(List<ClubQuestion> clubQuestions) {
        return clubQuestions.stream().map(ClubQuestionData::fromEntity).toList();
    }


}

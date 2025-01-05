package com.ariari.ariari.domain.club.question.answer.dto.req;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import lombok.Data;

@Data
public class ClubAnswerSaveReq {

    private String body;

    public ClubAnswer toEntity(ClubQuestion clubQuestion, ClubMember clubMember) {
        return new ClubAnswer(
                body,
                clubQuestion,
                clubMember
        );
    }

    public void modifyEntity(ClubAnswer clubAnswer) {
        clubAnswer.setBody(body);
    }

}

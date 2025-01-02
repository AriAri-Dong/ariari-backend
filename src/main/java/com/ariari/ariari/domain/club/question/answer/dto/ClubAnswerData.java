package com.ariari.ariari.domain.club.question.answer.dto;

import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClubAnswerData {

    private String body;

    private ClubMemberData clubMemberData;

    public static ClubAnswerData fromEntity(ClubAnswer clubAnswer) {
        return new ClubAnswerData(
                clubAnswer.getBody(),
                ClubMemberData.fromEntity(clubAnswer.getClubMember())
        );
    }

}

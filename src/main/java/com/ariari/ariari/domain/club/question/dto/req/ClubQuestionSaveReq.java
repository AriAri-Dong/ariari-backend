package com.ariari.ariari.domain.club.question.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.member.Member;
import lombok.Data;

@Data
public class ClubQuestionSaveReq {

    private String title;
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

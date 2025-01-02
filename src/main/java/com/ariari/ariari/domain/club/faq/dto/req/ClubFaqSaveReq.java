package com.ariari.ariari.domain.club.faq.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.faq.enums.ClubFaqColorType;
import lombok.Data;

@Data
public class ClubFaqSaveReq {

    private String title;
    private String body;
    private String clubFaqClassification;
    private ClubFaqColorType clubFaqColorType;

    public ClubFaq toEntity(Club club, ClubMember clubMember) {
        return new ClubFaq(
                title,
                body,
                clubFaqClassification,
                clubFaqColorType,
                club,
                clubMember
        );
    }

}

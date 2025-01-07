package com.ariari.ariari.domain.club.faq.dto;

import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.faq.enums.ClubFaqColorType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubFaqData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String body;
    private String clubFaqClassification;
    private ClubFaqColorType clubFaqColorType;

    private ClubMemberData clubMemberData;

    public static ClubFaqData fromEntity(ClubFaq clubFaq) {
        return new ClubFaqData(
                clubFaq.getId(),
                clubFaq.getTitle(),
                clubFaq.getBody(),
                clubFaq.getClubFaqClassification(),
                clubFaq.getClubFaqColorType(),
                ClubMemberData.fromEntity(clubFaq.getClubMember())
        );
    }

    public static List<ClubFaqData> fromEntities(List<ClubFaq> clubFaqs) {
        return clubFaqs.stream().map(ClubFaqData::fromEntity).toList();
    }

}

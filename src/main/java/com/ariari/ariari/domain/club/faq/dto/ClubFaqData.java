package com.ariari.ariari.domain.club.faq.dto;

import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.faq.enums.ClubFaqColorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubFaqData {

    private String title;
    private String body;
    private String clubFaqClassification;
    private ClubFaqColorType clubFaqColorType;

    public static ClubFaqData fromEntity(ClubFaq clubFaq) {
        return new ClubFaqData(
                clubFaq.getTitle(),
                clubFaq.getBody(),
                clubFaq.getClubFaqClassification(),
                clubFaq.getClubFaqColorType()
        );
    }

    public static List<ClubFaqData> fromEntities(List<ClubFaq> clubFaqs) {
        return clubFaqs.stream().map(ClubFaqData::fromEntity).toList();
    }

}

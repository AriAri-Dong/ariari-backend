package com.ariari.ariari.domain.club.faq.dto;

import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.faq.enums.ClubFaqColorType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 FAQ 데이터 (clubMemberData 는 작성자의 동아리 회원 데이터입니다.)")
public class ClubFaqData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 FAQ id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 FAQ 제목 (질문)", example = "아리아리에 가입하기 위해 필요한 서류는 무엇인가요?")
    private String title;
    @Schema(description = "동아리 FAQ 답변", example = "아리아리에 가입하기 위해서는 포트폴리오와 깃허브 URI를 제출해야 합니다.")
    private String body;
    @Schema(description = "동아리 FAQ 분류", example = "일정")
    private String clubFaqClassification;
    @Schema(description = "동아리 FAQ 색상 타입", example = "C_TOKEN_1")
    private ClubFaqColorType clubFaqColorType;

    public static ClubFaqData fromEntity(ClubFaq clubFaq) {
        return new ClubFaqData(
                clubFaq.getId(),
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

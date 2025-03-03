package com.ariari.ariari.domain.club.faq.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.faq.enums.ClubFaqColorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "동아리 FAQ 등록 형식")
public class ClubFaqSaveReq {

    @Schema(description = "동아리 FAQ 제목 (질문)", example = "아리아리에 가입하기 위해 필요한 서류는 무엇인가요?")
    private String title;
    @Schema(description = "동아리 FAQ 답변", example = "아리아리에 가입하기 위해서는 포트폴리오와 깃허브 URI를 제출해야 합니다.")
    private String body;
    @Schema(description = "동아리 FAQ 분류", example = "일정")
    private String clubFaqClassification;
    @Schema(description = "동아리 FAQ 색상 타입", example = "C_TOKEN_1")
    private ClubFaqColorType clubFaqColorType;

    public ClubFaq toEntity(Club club) {
        return new ClubFaq(
                title,
                body,
                clubFaqClassification,
                clubFaqColorType,
                club

        );
    }

}

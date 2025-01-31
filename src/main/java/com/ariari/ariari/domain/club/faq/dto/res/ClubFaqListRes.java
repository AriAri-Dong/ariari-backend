package com.ariari.ariari.domain.club.faq.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.faq.dto.ClubFaqData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 FAQ 리스트 응답")
public class ClubFaqListRes {

    @Schema(description = "동아리 FAQ 데이터 리스트")
    private List<ClubFaqData> clubFaqDataList;
    private PageInfo pageInfo;

    public static ClubFaqListRes fromEntities(Page<ClubFaq> page) {
        return new ClubFaqListRes(
                ClubFaqData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}

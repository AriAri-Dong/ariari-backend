package com.ariari.ariari.domain.club.faq.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.faq.dto.ClubFaqData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubFaqListRes {

    private List<ClubFaqData> contents;
    private PageInfo pageInfo;

    public static ClubFaqListRes fromEntities(Page<ClubFaq> page) {
        return new ClubFaqListRes(
                ClubFaqData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}

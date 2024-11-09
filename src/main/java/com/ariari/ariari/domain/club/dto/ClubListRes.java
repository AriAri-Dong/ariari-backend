package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.Club;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ClubListRes {

    private List<ClubData> contents;
    private PageInfo pageInfo;

    public static ClubListRes fromList(List<Club> clubs) {
        ClubListRes clubListRes = new ClubListRes();
        clubListRes.setContents(ClubData.fromEntities(clubs));
        return clubListRes;
    }

    public static ClubListRes fromPage(Page<Club> page) {
        ClubListRes clubListRes = new ClubListRes();
        clubListRes.setContents(ClubData.fromEntities(page.getContent()));
        clubListRes.setPageInfo(PageInfo.fromPage(page));
        return clubListRes;
    }

}

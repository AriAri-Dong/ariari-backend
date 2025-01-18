package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubNoticeListRes {

    private List<ClubNoticeData> clubNoticeDataList;
    private PageInfo pageInfo;

    public static  ClubNoticeListRes createRes(Page<ClubNotice> page) {
        return new ClubNoticeListRes(
                ClubNoticeData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

    public static  ClubNoticeListRes createRes(List<ClubNotice> clubNotices) {
        return new ClubNoticeListRes(
                ClubNoticeData.fromEntities(clubNotices),
                null
        );
    }

}

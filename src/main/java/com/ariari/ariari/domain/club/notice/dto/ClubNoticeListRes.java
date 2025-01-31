package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 공지사항 리스트 응답")
public class ClubNoticeListRes {

    @Schema(description = "동아리 공지사항 데이터 리스트")
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

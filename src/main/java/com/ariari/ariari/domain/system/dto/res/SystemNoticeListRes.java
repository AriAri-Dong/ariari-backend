package com.ariari.ariari.domain.system.dto.res;

import com.ariari.ariari.domain.system.dto.SystemNoticeData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "서비스 공지사항 리스트 응답")
public class SystemNoticeListRes {

    @Schema(description = "서비스 공지사항 데이터 리스트")
    private final List<SystemNoticeData> systemNoticeDataList;

    private SystemNoticeListRes(List<SystemNoticeData> systemNoticeDataList) {
        this.systemNoticeDataList = systemNoticeDataList;
    }

    public static SystemNoticeListRes create(List<SystemNoticeData> systemNoticeDataList) {
        return new SystemNoticeListRes(systemNoticeDataList);
    }
}




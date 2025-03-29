package com.ariari.ariari.domain.system.faq.dto.res;

import com.ariari.ariari.domain.system.faq.SystemFaq;
import com.ariari.ariari.domain.system.faq.dto.SystemFaqData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "서비스 FAQ 리스트 응답")
@Getter
public class SystemFaqListRes {

    @Schema(description = "서비스 FAQ 데이터 리스트")
    private final List<SystemFaqData> systemFaqDataList;

    private SystemFaqListRes(List<SystemFaqData> systemFaqDataList) {
        this.systemFaqDataList = systemFaqDataList;
    }

    public static SystemFaqListRes create(List<SystemFaq> systemFaqDataList) {
        List<SystemFaqData> systemFaqData = systemFaqDataList.stream().map(SystemFaqData::fromEntity).toList();
        return new SystemFaqListRes(systemFaqData);
    }
}

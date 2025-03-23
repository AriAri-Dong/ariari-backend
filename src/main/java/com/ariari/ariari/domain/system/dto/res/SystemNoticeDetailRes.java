package com.ariari.ariari.domain.system.dto.res;

import com.ariari.ariari.domain.system.SystemNotice;
import com.ariari.ariari.domain.system.dto.SystemNoticeData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "서비스 공지사항 상세 응답 (clubMemberData 는 작성한 동아리 회원의 데이터입니다.)")
@Getter
public class SystemNoticeDetailRes {

    private SystemNoticeData systemNoticeData;

    private SystemNoticeDetailRes(SystemNoticeData systemNoticeData) {
        this.systemNoticeData = systemNoticeData;
    }

    public static SystemNoticeDetailRes createRes(SystemNotice systemNotice){
        return new SystemNoticeDetailRes(SystemNoticeData.fromEntity(systemNotice));
    }
}

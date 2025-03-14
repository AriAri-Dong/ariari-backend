package com.ariari.ariari.domain.member.alarm.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.financial.FinancialRecord;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordData;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordListRes;
import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import com.ariari.ariari.domain.member.alarm.dto.MemberAlarmData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@Data
@Schema(description = "회원 알림 리스트 응답")
public class MemberAlarmListRes {

    @Schema(description = "회원 알림 데이터 리스트")
    private List<MemberAlarmData> memberAlarmDataList;

    private PageInfo pageInfo;


    private MemberAlarmListRes(List<MemberAlarmData> memberAlarmDataList, PageInfo pageInfo) {
        this.memberAlarmDataList = memberAlarmDataList;
        this.pageInfo = pageInfo;
    }

    public static MemberAlarmListRes fromPage(Page<MemberAlarm> page){
        // dto 변환작업
        List<MemberAlarmData> memberAlarmDataList = page.getContent().stream()
                .map(MemberAlarmData::fromEntity)
                .toList();
        if(memberAlarmDataList.isEmpty()){
            return new MemberAlarmListRes(
                    Collections.emptyList(),
                    PageInfo.fromPage(page)
            );
        }

        return new MemberAlarmListRes(memberAlarmDataList, PageInfo.fromPage(page));
    }

}


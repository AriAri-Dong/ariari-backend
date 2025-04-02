package com.ariari.ariari.domain.club.alarm.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.alarm.ClubAlarm;
import com.ariari.ariari.domain.club.alarm.dto.ClubAlarmData;
import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Data
@Schema(description = "동아리 알림 리스트 응답")
public class ClubAlarmListRes {

    @Schema(description = "동아리 알림 데이터 리스트")
    private List<ClubAlarmData> clubAlarmDataList;

    private PageInfo pageInfo;


    private ClubAlarmListRes(List<ClubAlarmData> clubAlarmDataList, PageInfo pageInfo) {
        this.clubAlarmDataList = clubAlarmDataList;
        this.pageInfo = pageInfo;
    }

    public static ClubAlarmListRes fromPage(Page<ClubAlarm> page){
        // dto 변환작업
        List<ClubAlarmData> clubAlarmDataList = page.getContent().stream()
                .map(ClubAlarmData::fromEntity)
                .toList();
        if(clubAlarmDataList.isEmpty()){
            return new ClubAlarmListRes(
                    Collections.emptyList(),
                    PageInfo.fromPage(page)
            );
        }

        return new ClubAlarmListRes(clubAlarmDataList, PageInfo.fromPage(page));
    }
}

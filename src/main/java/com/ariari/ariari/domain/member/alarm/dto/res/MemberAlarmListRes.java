package com.ariari.ariari.domain.member.alarm.dto.res;

import com.ariari.ariari.domain.member.alarm.dto.MemberAlarmData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "회원 알림 리스트 응답")
public class MemberAlarmListRes {

    @Schema(description = "회원 알림 데이터 리스트")
    private List<MemberAlarmData> memberAlarmDataList;

    public MemberAlarmListRes(List<MemberAlarmData> memberAlarmDataList) {
        this.memberAlarmDataList = memberAlarmDataList;
    }

    public static MemberAlarmListRes from(List<MemberAlarmData> memberAlarmDataList){
        return new MemberAlarmListRes(memberAlarmDataList);
    }
}

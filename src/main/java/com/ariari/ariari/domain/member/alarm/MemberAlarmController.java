package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.member.alarm.dto.MemberAlarmData;
import com.ariari.ariari.domain.member.alarm.dto.res.MemberAlarmListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "memberAlarm", description = "회원 알람 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberAlarmController {

    private final MemberAlarmService memberAlarmService;

    @Operation(summary = "회원 알림 조회")
    @GetMapping("/alarm")
    public MemberAlarmListRes getAlarms(@AuthenticationPrincipal CustomUserDetails userDetails
    , Pageable pageable){
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return memberAlarmService.getAlarms(memberId, pageable);
    }

    @Operation(summary = "회원 알림 읽음 표시", description = "회원이 알림을 읽으면 isChecked true 변경")
    @PatchMapping("/alarm/{memberAlarmId}/read")
    public void readAlarm(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long memberAlarmId){
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        memberAlarmService.readAlarm(memberId, memberAlarmId);
    }

    @Operation(summary = "회원 알림 삭제")
    @DeleteMapping("/alarm/{alarmId}")
    public void removeAlarm(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long alarmId){
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        memberAlarmService.removeAlarm(memberId, alarmId);
    }


}

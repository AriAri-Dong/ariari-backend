package com.ariari.ariari.domain.club.alarm;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.alarm.dto.res.ClubAlarmListRes;
import com.ariari.ariari.domain.club.clubmember.dto.res.ClubMemberListRes;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.member.alarm.dto.res.MemberAlarmListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "clubAlarm", description = "동아리 알림 기능")
@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubAlarmController {

    private final ClubAlarmService clubAlarmService;

    @Operation(summary = "동아리 알림 조회")
    @GetMapping("/{clubId}/alarm")
    public ClubAlarmListRes getAlarms(@AuthenticationPrincipal CustomUserDetails userDetails
            , @PathVariable Long clubId, Pageable pageable){
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        return clubAlarmService.getAlarms(memberId, clubId, pageable);
    }

    @Operation(summary = "동아리 알림 읽음 표시", description = "동아리 알림을 읽으면 isChecked true 변경")
    @PatchMapping("/{clubId}/alarm/{clubAlarmId}/read")
    public void readAlarm(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long clubId,  @PathVariable Long clubAlarmId){
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        clubAlarmService.readAlarm(memberId, clubId, clubAlarmId);
    }

    @Operation(summary = "동아리 알림 삭제")
    @DeleteMapping("/{clubId}/alarm/{clubAlarmId}")
    public void removeAlarm(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long clubId,  @PathVariable Long clubAlarmId){
        Long memberId = CustomUserDetails.getMemberId(userDetails, false);
        clubAlarmService.removeAlarm(memberId, clubId, clubAlarmId);
    }
}

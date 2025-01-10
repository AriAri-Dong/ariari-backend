package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "attendance-record", description = "출석 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 출석 수동 등록 (관리자)
    @Operation(summary = "출석 수동 등록", description = "관리자가 출석을 수동으로 등록합니다. 출석을 복수로 등록할 수 있습니다.")
    @PostMapping("/club-events/{clubEventId}/attendances")
    public void saveAttendanceByManager(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long clubEventId,
                                        @RequestBody List<Long> clubMemberIds) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        attendanceService.saveAttendancesByManager(reqMemberId, clubEventId, clubMemberIds);
    }

    // 출석 삭제 (관리자)
    @Operation(summary = "출석 삭제", description = "출석을 삭제합니다. 관리자만이 삭제할 수 있습니다. 출석을 복수로 삭제할 수 있습니다.")
    @DeleteMapping("/club-events/{clubEventId}/attendances")
    public void removeAttendance(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubEventId,
                                 @RequestBody List<Long> clubMemberIds) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        attendanceService.removeAttendances(reqMemberId, clubEventId, clubMemberIds);
    }

    // 출석 링크 생성

    // 출석하기 (동아리원이) by 출석 링크

}

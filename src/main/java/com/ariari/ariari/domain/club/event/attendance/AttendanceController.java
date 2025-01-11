package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.clubmember.dto.res.ClubMemberListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "attendance", description = "출석 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "출석 수동 등록", description = "관리자가 출석을 수동으로 등록합니다. 출석을 복수로 등록할 수 있습니다.")
    @PostMapping("/club-events/{clubEventId}/attendances")
    public void saveAttendanceByManager(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long clubEventId,
                                        @RequestBody List<Long> clubMemberIds) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        attendanceService.saveAttendancesByManager(reqMemberId, clubEventId, clubMemberIds);
    }

    @Operation(summary = "출석 삭제", description = "출석을 삭제합니다. 관리자만이 삭제할 수 있습니다. 출석을 복수로 삭제할 수 있습니다.")
    @DeleteMapping("/club-events/{clubEventId}/attendances")
    public void removeAttendance(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubEventId,
                                 @RequestBody List<Long> clubMemberIds) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        attendanceService.removeAttendances(reqMemberId, clubEventId, clubMemberIds);
    }

    @Operation(summary = "출석 키 생성", description = "출석 키를 생성합니다. 출석 키 유효 시간은 1시간입니다. 만료 이후에는 재발급 받아야 합니다.")
    @PostMapping("/club-events/{clubEventId}/attendances/key")
    public String issueAttendanceKey(@AuthenticationPrincipal CustomUserDetails userDetails,
                                     @PathVariable Long clubEventId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return attendanceService.issueAttendanceToken(reqMemberId, clubEventId);
    }

    @Operation(summary = "출석하기", description = "출석 키를 통해 출석합니다. 출석 키가 잘못되었거나, 만료되었을 경우 실패합니다.")
    @PostMapping("/attendances/key/{attendanceKey}")
    public void attendClubEvent(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable String attendanceKey) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        attendanceService.attendClubEvent(reqMemberId, attendanceKey);
    }

    @Operation(summary = "출석한 동아리 회원 리스트 조회", description = "출석한 동아리 회원 리스트를 조회합니다. 관리자만이 조회할 수 있습니다. (페이지네이션)")
    @GetMapping("/club-events/{clubEventId}/attendances")
    public ClubMemberListRes findAttendees(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable Long clubEventId,
                                           Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return attendanceService.findAttendees(reqMemberId, clubEventId, pageable);
    }

}

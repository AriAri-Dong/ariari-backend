package com.ariari.ariari.domain.club.event;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.event.dto.ClubEventListRes;
import com.ariari.ariari.domain.club.event.dto.ClubEventSaveReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "club-event", description = "동아리 일정 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClubEventController {

    private final ClubEventService clubEventService;

    @Operation(summary = "일정 등록", description = "일정을 등록합니다. 관리자만이 등록할 수 있습니다.")
    @PostMapping("/clubs/{clubId}/club-events")
    private void saveClubEvent(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubId,
                               @RequestBody ClubEventSaveReq saveReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubEventService.saveClubEvent(reqMemberId, clubId, saveReq);
    }

    @Operation(summary = "일정 수정", description = "일정을 수정합니다. 관리자만이 수정할 수 있습니다.")
    @PutMapping("/club-events/{clubEventId}")
    public void modifyClubEvent(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long clubEventId,
                                @RequestBody ClubEventSaveReq modifyReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubEventService.modifyClubEvent(reqMemberId, clubEventId, modifyReq);
    }

    @Operation(summary = "일정 삭제", description = "일정을 삭제합니다. 관리자만이 삭제할 수 있습니다.")
    @DeleteMapping("/club-events/{clubEventId}")
    public void removeClubEvent(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long clubEventId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubEventService.removeClubEvent(reqMemberId, clubEventId);
    }

    @Operation(summary = "일정 리스트 조회", description = "일정 리스트를 조회합니다. 동아리 회원만이 조회할 수 있습니다.")
    @GetMapping("/clubs/{clubId}/club-events")
    public ClubEventListRes findClubEvents(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable Long clubId,
                                           Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return clubEventService.findClubEvents(reqMemberId, clubId, pageable);
    }

}

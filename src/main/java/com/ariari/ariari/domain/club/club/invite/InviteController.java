package com.ariari.ariari.domain.club.club.invite;


import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.club.invite.dto.req.InviteAcceptRequest;
import com.ariari.ariari.domain.club.club.invite.dto.req.InviteAlarmRequest;
import com.ariari.ariari.domain.club.club.invite.dto.req.InviteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "club", description = "동아리 기능")
@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;
    @Operation(summary = "동아리 초대 알림 생성", description = "")
    @PostMapping("/inviteAlarm/")
    public void createInviteAlarm(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody InviteAlarmRequest inviteAlarmRequest){
        Long reqMemberId = getMemberId(userDetails, true);
        inviteService.createInviteAlarm(reqMemberId, inviteAlarmRequest);
    }

    @Operation(summary = "동아리 초대 알림 수락", description = "")
    @PostMapping("/{clubId}/AlarmAccept")
    public void acceptInviteAlarm(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody InviteAcceptRequest inviteAcceptRequest){
        Long reqMemberId = getMemberId(userDetails, true);
        inviteService.acceptInviteAlarm(reqMemberId, inviteAcceptRequest);
    }

    @Operation(summary = "동아리 초대 키 생성", description = "")
    @PostMapping("/{clubId}/invite")
    public String createInviteKey(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long clubId){
        Long reqMemberId = getMemberId(userDetails, true);
        return inviteService.createInviteKey(reqMemberId, clubId);
    }

    @Operation(summary = "동아리 초대 키 확인", description = "")
    @PostMapping("/enter")
    public String verifyInvite(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody InviteRequest inviteRequest){
        Long reqMemberId = getMemberId(userDetails, true);
        return inviteService.verifyInviteKey(reqMemberId, inviteRequest);
    }
}

package com.ariari.ariari.domain.club.club.invite;


import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.club.invite.dto.res.InviteDetailRes;
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

    @Operation(summary = "동아리 초대 키 생성", description = "")
    @PostMapping("/{clubId}/invite")
    public InviteDetailRes createInvite(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long clubId){
        Long reqMemberId = getMemberId(userDetails, true);
        return inviteService.createInvite(reqMemberId, clubId);
    }

    @Operation(summary = "동아리 초대 키 확인", description = "")
    @GetMapping("/enter")
    public void verifyInvite(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam String inviteKey){
        Long reqMemberId = getMemberId(userDetails, true);
        inviteService.verifyInviteKey(reqMemberId, inviteKey);
    }
}

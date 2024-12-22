package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.clubmember.dto.res.ClubMemberListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.*;

@RestController
@RequestMapping("/clubs/{clubId}/members")
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @GetMapping
    public ClubMemberListRes findClubMemberList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubMemberService.findClubMemberList(reqMemberId, clubId);
    }

}

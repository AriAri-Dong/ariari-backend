package com.ariari.ariari.domain.club.bookmark;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping("/clubs/{clubId}/bookmark")
@RequiredArgsConstructor
public class ClubBookmarkController {

    private final ClubBookmarkService clubBookmarkService;

    @PostMapping
    public void saveBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubBookmarkService.saveBookmark(reqMemberId, clubId);
    }

    @DeleteMapping
    public void removeBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubBookmarkService.removeBookmark(reqMemberId, clubId);
    }

}

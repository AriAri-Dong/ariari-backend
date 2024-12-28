package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping("/recruitments/{recruitmentId}/bookmark")
@RequiredArgsConstructor
public class RecruitmentBookmarkController {

    private final RecruitmentBookmarkService recruitmentBookmarkService;

    @PostMapping
    public void saveBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long recruitmentId) {
        Long reqMemberId = getMemberId(userDetails, true);
        recruitmentBookmarkService.saveBookmark(reqMemberId, recruitmentId);
    }

    @DeleteMapping
    public void removeBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long recruitmentId) {
        Long reqMemberId = getMemberId(userDetails, true);
        recruitmentBookmarkService.removeBookmark(reqMemberId, recruitmentId);
    }

}

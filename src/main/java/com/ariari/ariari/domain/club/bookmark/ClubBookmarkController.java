package com.ariari.ariari.domain.club.bookmark;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "club-bookmark", description = "동아리 북마크 기능")
@RestController
@RequestMapping("/clubs/{clubId}/bookmark")
@RequiredArgsConstructor
public class ClubBookmarkController {

    private final ClubBookmarkService clubBookmarkService;

    @Operation(summary = "동아리 북마크 등록", description = "")
    @PostMapping
    public void saveBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubBookmarkService.saveBookmark(reqMemberId, clubId);
    }

    @Operation(summary = "동아리 북마크 삭제", description = "")
    @DeleteMapping
    public void removeBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubBookmarkService.removeBookmark(reqMemberId, clubId);
    }

    @Operation(summary = "동아리 북마크 삭제", description = "")
    @DeleteMapping("/all")
    public void removeAllBookmarks(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubBookmarkService.removeAllBookmarks(reqMemberId);
    }

}

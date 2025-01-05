package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "recruitment-bookmark", description = "모집 북마크 기능")
@RestController
@RequestMapping("/recruitments/{recruitmentId}/bookmark")
@RequiredArgsConstructor
public class RecruitmentBookmarkController {

    private final RecruitmentBookmarkService recruitmentBookmarkService;

    @Operation(summary = "모집 북마크 등록", description = "")
    @PostMapping
    public void saveBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long recruitmentId) {
        Long reqMemberId = getMemberId(userDetails, true);
        recruitmentBookmarkService.saveBookmark(reqMemberId, recruitmentId);
    }

    @Operation(summary = "모집 북마크 삭제", description = "")
    @DeleteMapping
    public void removeBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long recruitmentId) {
        Long reqMemberId = getMemberId(userDetails, true);
        recruitmentBookmarkService.removeBookmark(reqMemberId, recruitmentId);
    }

}

package com.ariari.ariari.domain.club.faq;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.faq.dto.req.ClubFaqSaveReq;
import com.ariari.ariari.domain.club.faq.dto.res.ClubFaqListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "club-faq", description = "동아리 FAQ 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClubFaqController {

    private final ClubFaqService clubFaqService;

    @Operation(summary = "동아리 FAQ 리스트 조회", description = "동아리의 FAQ 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/clubs/{clubId}/club-faqs")
    public ClubFaqListRes findClubFaqs(@PathVariable Long clubId,
                                       Pageable pageable) {
        return clubFaqService.findClubFaqs(clubId, pageable);
    }

    @Operation(summary = "동아리 FAQ 등록", description = "동아리의 FAQ 를 등록합니다. 동아리 관리자만이 등록할 수 있습니다.")
    @PostMapping("/clubs/{clubId}/club-faqs")
    public void saveClubFaq(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable Long clubId,
                            @RequestBody ClubFaqSaveReq saveReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubFaqService.saveClubFaq(reqMemberId, clubId, saveReq);
    }

    @Operation(summary = "동아리 FAQ 삭제", description = "동아리의 FAQ 를 삭제합니다. 동아리 관리자만이 삭제할 수 있습니다.")
    @DeleteMapping("/club-faqs/{clubFaqId}")
    public void removeClubFaq(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable Long clubFaqId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubFaqService.removeClubFaq(reqMemberId, clubFaqId);
    }

}

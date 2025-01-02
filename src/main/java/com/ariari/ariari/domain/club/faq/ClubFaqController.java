package com.ariari.ariari.domain.club.faq;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.faq.dto.req.ClubFaqSaveReq;
import com.ariari.ariari.domain.club.faq.dto.res.ClubFaqListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs/{clubId}/clubFaqs")
@RequiredArgsConstructor
public class ClubFaqController {

    private final ClubFaqService clubFaqService;

    // 리스트 조회
    @GetMapping
    public ClubFaqListRes findClubFaqs(@PathVariable Long clubId,
                                       Pageable pageable) {
        return clubFaqService.findClubFaqs(clubId, pageable);
    }

    // 등록
    @PostMapping
    public void saveClubFaq(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable Long clubId,
                            @RequestBody ClubFaqSaveReq saveReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubFaqService.saveClubFaq(reqMemberId, clubId, saveReq);
    }

    // 삭제
    @DeleteMapping("/{clubFaqId}")
    public void removeClubFaq(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable Long clubFaqId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubFaqService.removeClubFaq(reqMemberId, clubFaqId);
    }


}

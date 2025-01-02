package com.ariari.ariari.domain.club.faq;

import com.ariari.ariari.domain.club.faq.dto.res.ClubFaqListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // 삭제

}

package com.ariari.ariari.domain.club.question;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.question.dto.req.ClubQuestionSaveReq;
import com.ariari.ariari.domain.club.question.dto.res.ClubQnaListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs/{clubId}/questions")
@RequiredArgsConstructor
public class ClubQuestionController {

    private final ClubQuestionService clubQuestionService;

    // 리스트 조회
    @GetMapping
    public ClubQnaListRes findClubQustions(Long clubId,
                                          Pageable pageable) {
        return clubQuestionService.findClubQuestions(clubId, pageable);
    }

    // 등록
    @PostMapping
    public void saveClubQuestion(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubId,
                                 @RequestBody ClubQuestionSaveReq saveReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubQuestionService.saveClubQuestion(reqMemberId, clubId, saveReq);
    }

    // 삭제
    @DeleteMapping("/{questionId}")
    public void removeClubQuestion(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable Long questionId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubQuestionService.removeClubQuestion(reqMemberId, questionId);
    }

}

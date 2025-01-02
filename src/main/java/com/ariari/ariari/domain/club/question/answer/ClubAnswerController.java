package com.ariari.ariari.domain.club.question.answer;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.question.answer.dto.req.ClubAnswerSaveReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs/{clubId}/question/{clubQuestionId}/answers")
@RequiredArgsConstructor
public class ClubAnswerController {

    private final ClubAnswerService clubAnswerService;

    // 등록
    @PostMapping
    public void saveClubAnswer(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubQuestionId,
                               @RequestBody ClubAnswerSaveReq saveReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubAnswerService.saveClubAnswer(reqMemberId, clubQuestionId, saveReq);
    }

    // 수정
    @DeleteMapping
    public void modifyClubAnswer(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubQuestionId,
                                 @RequestBody ClubAnswerSaveReq modifyReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubAnswerService.modifyClubAnswer(reqMemberId, clubQuestionId, modifyReq);
    }

}
package com.ariari.ariari.domain.club.question.answer;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.question.answer.dto.req.ClubAnswerSaveReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "club-answer", description = "동아리 질문 (Question) 에 대한 답변 (Answer) 기능")
@RestController
@RequestMapping("/clubs/{clubId}/question/{clubQuestionId}/answers")
@RequiredArgsConstructor
public class ClubAnswerController {

    private final ClubAnswerService clubAnswerService;

    @Operation(summary = "동아리 질문에 대한 답변 등록", description = "동아리 질문에 대한 답변을 등록합니다. 동아리 답변은 관리자 권한을 가진 동아리 회원만이 등록할 수 있습니다.")
    @PostMapping
    public void saveClubAnswer(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubQuestionId,
                               @RequestBody ClubAnswerSaveReq saveReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubAnswerService.saveClubAnswer(reqMemberId, clubQuestionId, saveReq);
    }

    @Operation(summary = "동아리 질문에 대한 답변 수정", description = "동아리 질문에 대한 답변 수정합니다.")
    @DeleteMapping
    public void modifyClubAnswer(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubQuestionId,
                                 @RequestBody ClubAnswerSaveReq modifyReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubAnswerService.modifyClubAnswer(reqMemberId, clubQuestionId, modifyReq);
    }

}
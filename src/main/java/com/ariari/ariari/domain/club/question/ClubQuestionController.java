package com.ariari.ariari.domain.club.question;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.question.dto.req.ClubQuestionSaveReq;
import com.ariari.ariari.domain.club.question.dto.res.ClubQnaListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "club-question", description = "동아리 Q&A 의 질문 (Question) 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClubQuestionController {

    private final ClubQuestionService clubQuestionService;

    @Operation(summary = "동아리 질문 리스트 조회", description = "동아리 질문 리스트를 조회합니다. 각 동아리 질문 데이터는 내부에 답변 (Answer) 데이터를 포함합니다.")
    @GetMapping("/clubs/{clubId}/club-questions")
    public ClubQnaListRes findClubQuestions(@PathVariable Long clubId,
                                            Pageable pageable) {
        return clubQuestionService.findClubQuestions(clubId, pageable);
    }

    @Operation(summary = "동아리 질문 등록", description = "동아리 질문을 등록합니다.")
    @PostMapping("/clubs/{clubId}/club-questions")
    public void saveClubQuestion(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubId,
                                 @RequestBody ClubQuestionSaveReq saveReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubQuestionService.saveClubQuestion(reqMemberId, clubId, saveReq);
    }

    @Operation(summary = "동아리 질문 삭제", description = "동아리 질문을 삭제합니다. 동아리 관리자 및 질문 작성자만이 질문을 삭제할 수 있습니다.")
    @DeleteMapping("club-questions/{questionId}")
    public void removeClubQuestion(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable Long questionId) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        clubQuestionService.removeClubQuestion(reqMemberId, questionId);
    }

}

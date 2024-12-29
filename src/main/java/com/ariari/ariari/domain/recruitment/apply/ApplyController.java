package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.recruitment.apply.dto.req.ApplySaveReq;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyDetailRes;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;
    private final ApplyListService applyListService;

    // 상세 조회
    @GetMapping("/applies/{applyId}")
    public ApplyDetailRes findApplyDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable Long applyId) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyService.findApplyDetail(reqMemberId, applyId);
    }

    // 등록
    @PostMapping("/recruitments/{recruitmentId}/applies")
    public void saveApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @PathVariable Long recruitmentId,
                          @RequestBody ApplySaveReq saveReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.saveApply(reqMemberId, recruitmentId, saveReq);
    }

    // 수정 : RequestParam = APPROVE, REFUSAL (PENDENCY x)
    @PatchMapping("/applies/{applyId}")
    public void processApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long applyId,
                             @RequestParam ApplyStatusType applyStatusType) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.processApply(reqMemberId, applyId, applyStatusType);
    }

    // club 참가

    // 삭제

    // 모집의 지원 리스트 조회

    // 내 지원 리스트 조회

}

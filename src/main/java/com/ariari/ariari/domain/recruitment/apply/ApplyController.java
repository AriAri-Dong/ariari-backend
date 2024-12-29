package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.recruitment.apply.dto.req.ApplySaveReq;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInTeamSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.req.MyAppliesSearchType;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyDetailRes;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    // 수정 : 합격 approve
    @PostMapping("/applies/{applyId}/approve")
    public void approveApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long applyId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.approveApply(reqMemberId, applyId);
    }

    // 수정 : 불합격 refuseApply
    @PostMapping("/applies/{applyId}/refuse")
    public void refuseApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long applyId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.refuseApply(reqMemberId, applyId);
    }

    // 수정 : 면접 중 processApply
    @PostMapping("/applies/{applyId}/interview")
    public void processApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long applyId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.processApply(reqMemberId, applyId);
    }

    // 삭제
    @DeleteMapping("/applies/{applyId}")
    public void removeApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable Long applyId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.removeApply(reqMemberId, applyId);
    }

    // 모집의 지원 리스트 조회
    @GetMapping("/club/{clubId}/applies")
    public ApplyListRes searchAppliesInClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable Long clubId,
                                            @ModelAttribute AppliesInTeamSearchCondition condition,
                                            Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyListService.searchAppliesInClub(reqMemberId, clubId, condition, pageable);
    }

    // 내 지원 리스트 조회
    @GetMapping("/applies/my")
    public ApplyListRes findMyApplies(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestParam MyAppliesSearchType searchType,
                                      Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyListService.findMyApplies(reqMemberId, pageable, searchType);
    }

}

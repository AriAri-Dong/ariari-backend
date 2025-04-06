package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.commons.exception.exceptions.InvalidListParamException;
import com.ariari.ariari.domain.recruitment.apply.dto.req.ApplySaveReq;
import com.ariari.ariari.domain.recruitment.apply.dto.req.AppliesInClubSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.req.MyAppliesSearchCondition;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyDetailRes;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "apply", description = "지원 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;
    private final ApplyListService applyListService;

    @Operation(summary = "지원 상세 조회", description = "")
    @GetMapping("/applies/{applyId}")
    public ApplyDetailRes findApplyDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable Long applyId) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyService.findApplyDetail(reqMemberId, applyId);
    }

    @Operation(summary = "지원 등록", description = "")
    @PostMapping(value = "/recruitments/{recruitmentId}/applies", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @PathVariable Long recruitmentId,
                          @RequestPart ApplySaveReq saveReq,
                          @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.saveApply(reqMemberId, recruitmentId, saveReq, file);
    }

    @Operation(summary = "지원 합격 처리", description = "해당 지원을 합격 처리합니다. 지원자가 동아리 회원으로 등록됩니다.")
    @PostMapping("/applies/approve")
    public void approveApplies(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestBody List<Long> applyIds) {
        Long reqMemberId = getMemberId(userDetails, true);
        if (applyIds == null || applyIds.isEmpty()) {
            throw new InvalidListParamException();
        }

        applyService.approveApplies(reqMemberId, applyIds);
    }

    @Operation(summary = "지원 거절 처리", description = "")
    @PostMapping("/applies/{applyId}/refuse")
    public void refuseApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @RequestBody List<Long> applyIds) {
        Long reqMemberId = getMemberId(userDetails, true);
        if (applyIds == null || applyIds.isEmpty()) {
            throw new InvalidListParamException();
        }

        applyService.refuseApply(reqMemberId, applyIds);
    }

    @Operation(summary = "지원 상태를 INTERVIEW 로 변경", description = "")
    @PostMapping("/applies/{applyId}/interview")
    public void processApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @RequestBody List<Long> applyIds) {
        Long reqMemberId = getMemberId(userDetails, true);
        if (applyIds == null || applyIds.isEmpty()) {
            throw new InvalidListParamException();
        }

        applyService.processApply(reqMemberId, applyIds);
    }

    @Operation(summary = "지원 삭제", description = "지원을 삭제합니다. 지원자 및 동아리 관리자만이 삭제할 수 있습니다.")
    @DeleteMapping("/applies/{applyId}")
    public void removeApply(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable Long applyId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyService.removeApply(reqMemberId, applyId);
    }

    @Operation(summary = "동아리의 지원 리스트 조회", description = "검색 조건에 따라 동아리의 지원 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/club/{clubId}/applies")
    public ApplyListRes searchAppliesInClub(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable Long clubId,
                                            @ModelAttribute AppliesInClubSearchCondition condition,
                                            Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        condition.validateCondition();
        return applyListService.searchAppliesInClub(reqMemberId, clubId, condition, pageable);
    }

    @Operation(summary = "내 지원 리스트 조회", description = "내 지원 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/applies/my")
    public ApplyListRes findMyApplies(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestParam(required = false) MyAppliesSearchCondition searchType,
                                      Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyListService.findMyApplies(reqMemberId, pageable, searchType);
    }

}

package com.ariari.ariari.domain.recruitment.applyform;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.recruitment.applyform.dto.ApplyFormModifyReq;
import com.ariari.ariari.domain.recruitment.applyform.dto.ApplyFormRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "apply-form", description = "지원 형식 기능 (Club <-> ApplyForm -> 1:다 형식입니다. 조회할 때엔 마지막으로 등록된 1개의 지원 형식을 조회합니다.)")
@RestController
@RequestMapping("/clubs/{clubId}/apply-forms")
@RequiredArgsConstructor
public class ApplyFormController {

    private final ApplyFormService applyFormService;

    @Operation(summary = "지원 형식 조회", description = "지원 형식을 조회합니다. (가장 최신 형식을 조회합니다. 등록된 지원 형식이 없을 경우 null 반환합니다.) 관리자만이 지원 형식을 조회할 수 있습니다.")
    @GetMapping
    public ApplyFormRes findApplyForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable Long clubId) {
        Long memberId = getMemberId(userDetails, true);
        return applyFormService.findApplyForm(memberId, clubId);
    }

    @Operation(summary = "지원 형식 수정", description = "지원 형식을 수정합니다. 사실상 지원 형식이 새로 등록됩니다. 관리자만이 지원 형식을 수정할 수 있습니다.")
    @PostMapping
    public void modifyApplyForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long clubId,
                                @RequestBody ApplyFormModifyReq modifyReq) {
        Long memberId = getMemberId(userDetails, true);
        applyFormService.modifyApplyForm(memberId, clubId, modifyReq);
    }

}

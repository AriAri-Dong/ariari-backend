package com.ariari.ariari.domain.recruitment.applyform;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.recruitment.applyform.dto.ApplyFormModifyReq;
import com.ariari.ariari.domain.recruitment.applyform.dto.ApplyFormRes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping("/clubs/{clubId}/apply-form")
@RequiredArgsConstructor
public class ApplyFormController {

    private final ApplyFormService applyFormService;

    @GetMapping
    public ApplyFormRes findApplyForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable Long clubId) {
        Long memberId = getMemberId(userDetails, true);
        return applyFormService.findApplyForm(memberId, clubId);
    }

    // 사실상 새로 등록하는 기능
    @PutMapping
    public void modifyApplyForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long clubId,
                                @RequestBody ApplyFormModifyReq modifyReq) {
        Long memberId = getMemberId(userDetails, true);
        applyFormService.modifyApplyForm(memberId, clubId, modifyReq);
    }

}

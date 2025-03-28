package com.ariari.ariari.domain.system.faq;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.system.faq.dto.req.SystemFaqModifyReq;
import com.ariari.ariari.domain.system.faq.dto.req.SystemFaqSaveReq;
import com.ariari.ariari.domain.system.faq.dto.res.SystemFaqListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "system_faq", description = "서비스 FAQ 기능")
@RestController
@RequiredArgsConstructor
public class SystemFaqController {

    private final SystemFaqService systemFaqService;

    @Operation(summary = "서비스 공지사항 리스트 조회", description = "운영 관리자만이 조회할 수 있습니다.")
    @GetMapping("/service-faqs")
    public SystemFaqListRes findSystemFaqs() {
        return systemFaqService.findSystemFaqs();
    }

    @Operation(summary = "서비스 FAQ 등록", description = "운영 관리자만이 등록할 수 있습니다.")
    @PostMapping(value = "/service-faq/create")
    public void saveSystemFaq(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestBody SystemFaqSaveReq systemFaq){
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        systemFaqService.saveSystemNotice(reqMemberId, systemFaq);
    }

    @Operation(summary = "서비스 FAQ 수정", description = "운영 관리자만이 수정할 수 있습니다.")
    @PatchMapping(value = "/service-faq/{systemFaqId}")
    public void modifySystemNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable Long systemFaqId,
                                   @RequestBody SystemFaqModifyReq modifyReq) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        systemFaqService.modifySystemFaq(reqMemberId, systemFaqId, modifyReq);
    }


    @Operation(summary = "서비스 FAQ 등록", description = "운영 관리자만이 등록할 수 있습니다.")
    @DeleteMapping(value = "/service-faq/{systemFaqId}")
    public void removeSystemFaq(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable Long systemFaqId ){
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        systemFaqService.removeSystemFaq(reqMemberId, systemFaqId);
    }
}

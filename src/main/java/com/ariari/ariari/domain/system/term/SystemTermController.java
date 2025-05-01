package com.ariari.ariari.domain.system.term;

import com.ariari.ariari.domain.system.enums.TermType;
import com.ariari.ariari.domain.system.term.dto.res.SystemTermDetailRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "system_term", description = "서비스 약관 기능")
@RestController
@RequestMapping("/system-term")
@RequiredArgsConstructor
public class SystemTermController {
    private final SystemTermService systemTermService;

    @Operation(summary = "약관 유형으로 약관 조회", description = "PRIVACY_POLICY : 개인정보 처리방침 , CLUB_RULES : 동아리 이용수칙, PLATFORM_RULES : 플랫폼 이용수칙")
    @GetMapping("/{termType}")
    public SystemTermDetailRes findSystemNoticeDetail(@PathVariable(value = "termType") TermType termType) {
        return systemTermService.getSystemTermByTermType(termType);
    }
}

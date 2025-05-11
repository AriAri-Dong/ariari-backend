package com.ariari.ariari.domain.system.term.dto.res;

import com.ariari.ariari.domain.system.SystemTerm;
import com.ariari.ariari.domain.system.enums.TermType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SystemTermDetailRes {
    @Schema(description = "pk", example = "")
    private Long id;

    @Schema(description = "약관 종류 : PRIVACY_POLICY,         // 개인정보처리방침\n" +
            "    CLUB_RULES,             // 동아리 이용수칙\n" +
            "    PLATFORM_RULES         // 플랫폼 이용수칙", example = "PRIVACY_POLICY")
    private TermType termType;

    @Schema(description = "본문", example = "")
    private String body;

    public static SystemTermDetailRes fromEntity(SystemTerm systemTerm) {
        return SystemTermDetailRes.builder()
                .id(systemTerm.getId())
                .termType(systemTerm.getTermType())
                .body(systemTerm.getBody())
                .build();
    }
}

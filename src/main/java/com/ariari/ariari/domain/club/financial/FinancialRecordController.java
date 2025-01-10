package com.ariari.ariari.domain.club.financial;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordListRes;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordSaveReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "financial-record", description = "회계 기록 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;

    @Operation(summary = "회계 잔액 조회", description = "회계 잔액을 조회합니다.")
    @GetMapping("/clubs/{clubId}/financial-records/balance")
    public Long findBalance(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable Long clubId) {
        Long reqMemberId = getMemberId(userDetails, true);
        return financialRecordService.findBalance(reqMemberId, clubId);
    }

    @Operation(summary = "회계 기록 등록", description = "동아리 관리자만이 사용할 수 있으며, 동아리 잔액에 영향을 미칩니다. 수입 지출 구분 필드 없이 +/- 로 구분됩니다. 잔액은 음수가 될 수 있습니다.")
    @PostMapping("/clubs/{clubId}/financial-records")
    public void saveFinancialRecord(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable Long clubId,
                                    @RequestBody FinancialRecordSaveReq saveReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        financialRecordService.saveFinancialRecord(reqMemberId, clubId, saveReq);
    }

    @Operation(summary = "회계 기록 리스트 조회", description = "(페이지네이션)")
    @GetMapping("/clubs/{clubId}/financial-records")
    public FinancialRecordListRes findFinancialRecords(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @PathVariable Long clubId,
                                                       Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return financialRecordService.findFinancialRecords(reqMemberId, clubId, pageable);
    }

    /* ================== 비활성화 기능 ================== */

    @Operation(summary = "회계 기록 수정", description = "동아리 관리자만이 사용할 수 있으며, 동아리 잔액에 영향을 미칩니다. 수입 지출 구분 필드 없이 +/- 로 구분됩니다. 잔액은 음수가 될 수 없습니다. (해당 API 불필요할 시 비활성화 하겠습니다.)")
//    @PutMapping("/{financialRecordId}")
    public void modifyFinancialRecord(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable Long clubId,
                                      @PathVariable Long financialRecordId,
                                      @RequestBody FinancialRecordSaveReq saveReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        financialRecordService.modifyFinancialRecord(reqMemberId, clubId, financialRecordId, saveReq);
    }

    @Operation(summary = "회계 기록 삭제", description = "동아리 관리자만이 사용할 수 있으며, 동아리 잔액에 영향을 미칩니다. 수입 지출 구분 필드 없이 +/- 로 구분됩니다. 잔액은 음수가 될 수 없습니다. (해당 API 불필요할 시 비활성화 하겠습니다.")
//    @DeleteMapping("/{financialRecordId}")
    public void removeFinancialRecord(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable Long clubId,
                                      @PathVariable Long financialRecordId) {
        Long reqMemberId = getMemberId(userDetails, true);
        financialRecordService.removeFinancialRecord(reqMemberId, clubId, financialRecordId);
    }

}

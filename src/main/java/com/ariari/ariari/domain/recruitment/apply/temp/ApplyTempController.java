package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempModifyReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempSaveReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempDetailRes;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ApplyTempController {

    private final ApplyTempService applyTempService;
    private final ApplyTempListService applyTempListService;

    // 상세 조회
    @GetMapping("/apply-temps/{applyTempId}")
    public ApplyTempDetailRes findApplyTempDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable Long applyTempId) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyTempService.findApplyTempDetail(reqMemberId, applyTempId);
    }

    // 등록
    @PostMapping("/recruitments/{recruitmentId}/apply-temps")
    public void saveApplyTemp(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable Long recruitmentId,
                              @RequestPart ApplyTempSaveReq saveReq,
                              @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyTempService.saveApplyTemp(reqMemberId, recruitmentId, saveReq, file);
    }

    // 수정
    @PutMapping("/apply-temp/{applyTempId}")
    public void modifyApplyTemp(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long applyTempId,
                                @RequestPart ApplyTempModifyReq modifyReq,
                                @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyTempService.modifyApplyTemp(reqMemberId, applyTempId, modifyReq, file);
    }

    // 삭제
    @DeleteMapping("/apply-temp/{applyTempId}")
    public void removeApplyTemp(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long applyTempId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyTempService.removeApplyTemp(reqMemberId, applyTempId);
    }

    // 포트폴리오 파일 삭제
    @PostMapping("/apply-temp/{applyTempId}/delete-portfolio")
    public void deletePortfolio(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long applyTempId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyTempService.deletePortfolio(reqMemberId, applyTempId);
    }

    // 내 임시 지원 리스트 조회
    @GetMapping("/apply-temps/my")
    public ApplyTempListRes findMyApplyTemps(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyTempListService.findMyApplyTemps(reqMemberId, pageable);
    }

}

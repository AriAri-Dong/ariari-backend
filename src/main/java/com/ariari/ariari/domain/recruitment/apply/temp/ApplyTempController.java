package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempModifyReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.req.ApplyTempSaveReq;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempDetailRes;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "apply-temp", description = "임시 지원 기능 (임시 지원 기능은 작성자 본인만이 사용할 수 있습니다.")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ApplyTempController {

    private final ApplyTempService applyTempService;

    @Operation(summary = "임시 지원 상세 조회", description = "")
    @GetMapping("/apply-temps/{applyTempId}")
    public ApplyTempDetailRes findApplyTempDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable Long applyTempId) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyTempService.findApplyTempDetail(reqMemberId, applyTempId);
    }

    @Operation(summary = "임시 지원 저장", description = "포트폴리오는 파일과 URI 중 하나만 저장할 수 있습니다. 우선순위는 URI 입니다. 둘 다 담길 경우 URI 만을 저장합니다.")
    @PostMapping(value = "/recruitments/{recruitmentId}/apply-temps", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveApplyTemp(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable Long recruitmentId,
                              @RequestPart ApplyTempSaveReq saveReq,
                              @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyTempService.saveApplyTemp(reqMemberId, recruitmentId, saveReq, file);
    }

    @Operation(summary = "임시 지원 수정", description = "포트폴리오는 파일과 URI 중 하나만 저장할 수 있습니다. 우선순위는 URI 입니다. 기존에 파일이 저장되어 있는 상태에서 DTO 에 URI 필드가 담길 경우 기존 파일은 삭제되고 URI 만을 저장합니다.")
    @PutMapping(value = "/apply-temp/{applyTempId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void modifyApplyTemp(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long applyTempId,
                                @RequestPart ApplyTempModifyReq modifyReq,
                                @RequestPart(required = false) MultipartFile file) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyTempService.modifyApplyTemp(reqMemberId, applyTempId, modifyReq, file);
    }

    @Operation(summary = "임시 지원 삭제", description = "")
    @DeleteMapping("/apply-temp/{applyTempId}")
    public void removeApplyTemp(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long applyTempId) {
        Long reqMemberId = getMemberId(userDetails, true);
        applyTempService.removeApplyTemp(reqMemberId, applyTempId);
    }

    @Operation(summary = "내 임시 지원 리스트 조회", description = "")
    @GetMapping("/apply-temps/my")
    public ApplyTempListRes findMyApplyTemps(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return applyTempService.findMyApplyTemps(reqMemberId, pageable);
    }

}

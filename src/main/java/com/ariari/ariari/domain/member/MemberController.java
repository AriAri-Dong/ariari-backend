package com.ariari.ariari.domain.member;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.member.dto.req.NicknameModifyReq;
import com.ariari.ariari.domain.member.dto.req.ProfileModifyReq;
import com.ariari.ariari.domain.member.dto.res.MemberDetailRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "member", description = "회원 기능")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 회원 정보 조회")
    @GetMapping
    public MemberDetailRes findMyMemberDetail(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = getMemberId(userDetails, true);
        return memberService.findMyMemberDetail(reqMemberId);
    }

    @Operation(summary = "회원 닉네임 수정", description = "회원 닉네임은 중복이 불가능합니다. 만약 중복되는 닉네임일 경우 예외가 발생합니다.")
    @PutMapping("/nickname")
    public void modifyNickname(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestBody NicknameModifyReq modifyReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        memberService.modifyNickname(reqMemberId, modifyReq);
    }

    @Operation(summary = "회원 프로필 수정")
    @PutMapping("/profile")
    public void modifyProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestBody ProfileModifyReq modifyReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        memberService.modifyProfile(reqMemberId, modifyReq);
    }

}

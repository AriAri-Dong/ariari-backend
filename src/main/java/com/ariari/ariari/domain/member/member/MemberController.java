package com.ariari.ariari.domain.member.member;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.member.member.dto.req.NicknameModifyReq;
import com.ariari.ariari.domain.member.member.dto.req.ProfileModifyReq;
import com.ariari.ariari.domain.member.member.dto.res.MemberDetailRes;
import com.ariari.ariari.domain.member.member.dto.res.MemberListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@Tag(name = "member", description = "회원 기능")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 회원 정보 조회")
    @GetMapping("/my")
    public MemberDetailRes findMyMemberDetail(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqMemberId = getMemberId(userDetails, true);
        return memberService.findMyMemberDetail(reqMemberId);
    }

    @Operation(summary = "회원 닉네임 수정", description = "회원 닉네임은 중복이 불가능합니다. 만약 중복되는 닉네임일 경우 예외가 발생합니다.")
    @PutMapping("/my/nickname")
    public void modifyNickname(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestBody NicknameModifyReq modifyReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        memberService.modifyNickname(reqMemberId, modifyReq);
    }

    @Operation(summary = "회원 프로필 수정")
    @PutMapping("/my/profile")
    public void modifyProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestBody ProfileModifyReq modifyReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        memberService.modifyProfile(reqMemberId, modifyReq);
    }

    @Operation(summary = "닉네임으로 회원 리스트 검색", description = "닉네임을 contains 검색합니다. 닉네임으로 정렬되며 최대 20개의 데이터를 반환합니다.")
    @GetMapping
    public MemberListRes searchMemberList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestParam String nickname) {
        Long reqMemberId = getMemberId(userDetails, true);
        return memberService.searchMembers(reqMemberId, nickname);
    }
}

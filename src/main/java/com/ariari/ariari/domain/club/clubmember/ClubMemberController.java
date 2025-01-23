package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.clubmember.dto.res.ClubMemberListRes;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.club.clubmember.exception.ModifyingClubMemberRoleTypeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.*;

@Tag(name = "club-member", description = "동아리 회원 기능")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @Operation(summary = "동아리 회원 리스트 조회", description = "동아리 내 전체 회원 리스트를 조회합니다. (페이지네이션)")
    @GetMapping("/clubs/{clubId}/club-members")
    public ClubMemberListRes findClubMemberList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @PathVariable Long clubId,
                                                @RequestParam(required = false) ClubMemberStatusType statusType,
                                                Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubMemberService.findClubMemberList(reqMemberId, clubId, statusType, pageable);
    }

    @Operation(summary = "동아리 회원 권한 수정", description = "동아리 회원 권한을 수정합니다. 최고 관리자 권한은 [최고 관리자 권한 위임] API를 사용해야 합니다. '스텝' 권한 이상의 동아리 회원이 타 동아리 회원의 권한을 수정할 수 있습니다.")
    @PutMapping("/club-members/{clubMemberId}/role")
    public void modifyRoleType(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long clubMemberId,
                               @RequestBody ClubMemberRoleType roleType) {
        Long reqMemberId = getMemberId(userDetails, true);

        if (roleType.equals(ClubMemberRoleType.ADMIN)) {
            throw new ModifyingClubMemberRoleTypeException();
        }

        clubMemberService.modifyRoleType(reqMemberId, clubMemberId, roleType);
    }

    @Operation(summary = "최고 관리자 권한 위임", description = "최고 관리자 권한을 위임합니다. '최고 관리자' 동아리 회원만이 최고 관리자 권한을 위임할 수 있습니다.")
    @PatchMapping("/club_members/{clubMemberId}/entrust-admin")
    public void entrustAdmin(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long clubMemberId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubMemberService.entrustAdmin(reqMemberId, clubMemberId);
    }

    @Operation(summary = "동아리 회원 상태 수정", description = "1명의 동아리 회원 상태를 수정합니다.")
    @PutMapping("/club-members/{clubMemberId}/status")
    public void modifyStatusType(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubMemberId,
                                 @RequestBody ClubMemberStatusType statusType) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubMemberService.modifyStatusType(reqMemberId, clubMemberId, statusType);
    }

    @Operation(summary = "동아리 회원 상태 배치 수정", description = "여러 동아리 회원 상태를 수정합니다.")
    @PutMapping("/clubs/{clubId}/club-members/status")
    public void modifyStatusTypes(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable Long clubId,
                                  @RequestParam List<Long> clubMemberIds,
                                  @RequestBody ClubMemberStatusType statusType) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubMemberService.modifyStatusTypes(reqMemberId, clubId, clubMemberIds, statusType);
    }

    @Operation(summary = "동아리 회원 삭제", description = "동아리 회원을 삭제합니다.")
    @DeleteMapping("/club-members/{clubMemberId}")
    public void removeClubMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long clubMemberId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubMemberService.removeClubMember(reqMemberId, clubMemberId);
    }

    @Operation(summary = "동아리 회원 검색", description = "query 로 동아리 내 회원을 검색합니다. (contains 방식)")
    @GetMapping("/clubs/{clubId}/club-members/search")
    public ClubMemberListRes searchClubMembers(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long clubId,
                                               @RequestParam String query,
                                               Pageable pageable) {
        Long reqMemberId = getMemberId(userDetails, true);
        return clubMemberService.searchClubMembers(reqMemberId, clubId, query, pageable);
    }

    // (추가 예정) 동아리 회원 초대 코드 생성

    // (추가 예정) 동아리 회원 초대 (입장)

    // (추가 예정) 동아리 회원 이름 검색

}

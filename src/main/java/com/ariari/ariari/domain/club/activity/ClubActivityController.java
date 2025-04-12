package com.ariari.ariari.domain.club.activity;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.activity.dto.req.ClubActivityModifyReq;
import com.ariari.ariari.domain.club.activity.dto.req.ClubActivitySaveReq;
import com.ariari.ariari.domain.club.activity.dto.req.CommentReq;
import com.ariari.ariari.domain.club.activity.dto.res.ClubActivityDetailRes;
import com.ariari.ariari.domain.club.activity.dto.res.ClubActivityListRes;
import com.ariari.ariari.domain.club.passreview.dto.req.PassReviewSaveReq;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails.getMemberId;

@RestController
@RequestMapping("/club-activity")
@RequiredArgsConstructor
public class ClubActivityController {
    private final ClubActivityService clubActivityService;

    @GetMapping("/{clubId}")
    @Operation(summary = "활동내역 목록 조회", description = "활동내역을 목록 조회한다.")
    public ClubActivityListRes read_club_activity_page(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       Pageable pageable, @PathVariable(name = "clubId") Long clubId) {
        Long reqMemberId = getMemberId(userDetails, false);
        return clubActivityService.readClubActivityPage(reqMemberId, clubId, pageable);
    }

    @PostMapping("/like/{clubActivityId}")
    @Operation(summary = "활동내역 좋아요/좋아요 취소", description = "해당 활동내역에 대해서 좋아요 or 좋아요 취소")
    public void save_club_activity_like(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable("clubActivityId") Long clubActivityId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.saveClubActivityLike(reqMemberId, clubActivityId);
    }

    @GetMapping("/detail/{clubActivityId}")
    @Operation(summary = "활동내역 상세 조회", description = "활동내역을 상세 조회한다.")
    public ClubActivityDetailRes read_club_activity_detail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable(name = "clubActivityId") Long clubActivityId){
        Long reqMemberId = getMemberId(userDetails, false);
        return clubActivityService.readClubActivityDetail(reqMemberId, clubActivityId);
    }

    @PostMapping(value = "/{clubId}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "활동내역 작성하기", description = "활동내역 데이터를 생성한다.")
    public void save_club_activity(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "clubId") Long clubId,
                                   @ModelAttribute @Valid ClubActivitySaveReq clubActivitySaveReq){
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.saveClubActivity(reqMemberId, clubId, clubActivitySaveReq);
    }

    @PutMapping(value ="/detail/{clubActivityId}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "활동내역 수정", description = "활동내역을 수정한다.")
    public void modify_club_activity_detail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable(name = "clubActivityId") Long clubActivityId,
                                            @ModelAttribute @Valid ClubActivityModifyReq clubActivityModifyReq){
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.modifyClubActivity(clubActivityId, reqMemberId, clubActivityModifyReq);
    }

    @DeleteMapping("/detail/{clubActivityId}")
    @Operation(summary = "활동내역 삭제", description = "활동내역을 수정한다.")
    public void delete_club_activity_detail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable(name = "clubActivityId") Long clubActivityId){
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.deleteClubActivity(reqMemberId, clubActivityId);
    }

    @PostMapping("/comment/{clubActivityId}")
    @Operation(summary = "활동 내역 댓글 작성", description = "해당 활동내역에 대해서 좋아요 or 좋아요 취소")
    public void save_club_activity_comment(@PathVariable("clubActivityId") Long clubActivityId,
                                           @AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestParam(value = "parentCommentId", required = false) Long parentCommentId,
                                           @RequestBody @Valid CommentReq commentReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.saveClubActivityComment(reqMemberId, clubActivityId, parentCommentId, commentReq);
    }

    @PutMapping("/comment/{clubActivityId}")
    @Operation(summary = "활동 내역 댓글 수정", description = "해당 활동내역에 대해서 좋아요 or 좋아요 취소")
    public void modify_club_activity_comment(@PathVariable("clubActivityId") Long clubActivityId,
                                             @AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestParam(value = "commentId") Long commentId,
                                             @RequestBody @Valid CommentReq commentReq) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.modifyClubActivityComment(reqMemberId, clubActivityId, commentId, commentReq);
    }

    @DeleteMapping("/comment/{clubActivityId}")
    @Operation(summary = "활동 내역 댓글 삭제", description = "해당 활동내역에 대해서 좋아요 or 좋아요 취소")
    public void delete_club_activity_comment(@PathVariable("clubActivityId") Long clubActivityId,
                                             @AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestParam(value = "commentId") Long commentId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.deleteClubActivityComment(reqMemberId, clubActivityId, commentId);
    }

    @PostMapping("/comment/like/{clubActivityId}")
    @Operation(summary = "활동 내역 댓글 좋아요 추가/삭제", description = "해당 활동내역에 대해서 좋아요 or 좋아요 취소")
    public void save_club_activity_comment_like(@PathVariable("clubActivityId") Long clubActivityId,
                                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestParam(value = "commentId") Long commentId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.saveClubActivityCommentLike(reqMemberId, clubActivityId, commentId);
    }

    @PostMapping("/comment/block")
    @Operation(summary = "활동 내역 댓글 유저 차단", description = "해당 활동내역에 대해서 차단")
    public void save_club_activity_comment_block(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestParam(value = "commentId") Long commentId) {
        Long reqMemberId = getMemberId(userDetails, true);
        clubActivityService.saveClubActivityCommentBlock(reqMemberId, commentId);
    }
}

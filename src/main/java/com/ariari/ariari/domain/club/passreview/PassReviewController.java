package com.ariari.ariari.domain.club.passreview;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.club.passreview.service.PassReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pass-review")
@RequiredArgsConstructor
public class PassReviewController {
    private final PassReviewService passReviewService;

    // 패스리뷰 리스트
    // 필요한 요소 : 페이지네이션으로 id, 제목, 작성일자, 서류 관련 답변 개수, 면접관련 답변개수, 유저의 열람 여부

    // 패스 디테일
    // 필요한 요소 : 제목, 서류 or 서류면접 , 서류문항들(제목,답변), 면접방식, 면접인원, 면접분위기, 면접문항들(제목,답변)

    // 포인트쪽 처리 도메인 왜냐면 포인트를 처리해야함~

    // 합격후기 등록하기 -> 권한 검사

//    @GetMapping
//    public PassReviewListRes searchPassReviewPage(@AuthenticationPrincipal CustomUserDetails userDetails){
//
//    }
}

package com.ariari.ariari.domain.school;

import com.ariari.ariari.commons.auth.springsecurity.CustomUserDetails;
import com.ariari.ariari.domain.school.dto.SchoolListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "school", description = "학교 기능")
@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "학교 리스트 검색 조회", description = "학교 리스트를 검색어로 조회합니다. 검색 파라미터(query)를 비울 수 있습니다. (페이지네이션)")
    @GetMapping
    public SchoolListRes searchSchools(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestParam(required = false) String query,
                                       Pageable pageable) {
        Long reqMemberId = CustomUserDetails.getMemberId(userDetails, true);
        return schoolService.searchSchools(reqMemberId, query, pageable);
    }

}

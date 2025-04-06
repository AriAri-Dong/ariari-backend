package com.ariari.ariari.domain.recruitment.apply.dto.req;

import com.ariari.ariari.domain.recruitment.apply.exceptions.SearchAppliesInClubException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "동아리의 지원서 리스트 검색 조건 (각 조건은 and 검색됩니다.)")
public class AppliesInClubSearchCondition {

    @Schema(description = "ApplyStatusType 이 PENDENCY 인 지원서를 조회할 지 여부", example = "false")
    private Boolean isPendent;
    @Schema(description = "검색어 파라미터 (회원 닉네임, 지원서 이름, 모집 제목에 대해 contains 조회)", example = "아리")
    private String query;
    @Schema(description = "모집의 시작 날짜/시간", example = "2025-01-15T09:08:18.467Z")
    private LocalDateTime startDateTime;
    @Schema(description = "모집의 종료 날짜/시간", example = "2025-05-15T09:08:18.467Z")
    private LocalDateTime endDateTime;

    public void validateCondition() {
        if ((startDateTime == null && endDateTime != null) || (startDateTime != null && endDateTime == null)) {
            throw new SearchAppliesInClubException();
        }

        if (startDateTime != null && startDateTime.isAfter(endDateTime)) {
            throw new SearchAppliesInClubException();
        }
    }

}

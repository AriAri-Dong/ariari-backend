package com.ariari.ariari.domain.club.passreview.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PassReviewListRes {
    @Schema(description = "데이터", example = "합격후기 목록 관련 데이터")
    private List<PassReviewRes> contents;
    @Schema(description = "페이지 관련 정보", example = "페이지 관련 정보")
    private PageInfo pageInfo;

    public static PassReviewListRes fromPassReviewResList(List<PassReviewRes> contents, Integer totalSize, Integer pageSize) {
        return new PassReviewListRes(
                contents,
                PageInfo.fromOther(contents.size(), totalSize, pageSize)
        );
    }

}

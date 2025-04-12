package com.ariari.ariari.domain.club.activity.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityData;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewListRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubActivityListRes {
    @Schema(description = "활동후기 리스트", example = "")
    private List<ClubActivityData> clubActivityDataList;

    @Schema(description = "페이지 관련 정보", example = "")
    private PageInfo pageInfo;

    public static ClubActivityListRes fromClubActivityList(List<ClubActivityData> contents, Integer totalSize, Integer pageSize) {
        return new ClubActivityListRes(
                contents,
                PageInfo.fromOther(contents.size(), totalSize, pageSize)
        );
    }
}

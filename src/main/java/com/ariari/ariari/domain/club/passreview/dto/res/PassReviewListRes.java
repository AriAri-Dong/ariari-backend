package com.ariari.ariari.domain.club.passreview.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.dto.ClubData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class PassReviewListRes {
    private List<PassReviewRes> contents;
    private PageInfo pageInfo;

    public static PassReviewListRes fromPassReviewResList(List<PassReviewRes> contents, Integer totalSize, Integer pageSize) {
        return new PassReviewListRes(
                contents,
                PageInfo.fromOther(contents.size(), totalSize, pageSize)
        );
    }

}

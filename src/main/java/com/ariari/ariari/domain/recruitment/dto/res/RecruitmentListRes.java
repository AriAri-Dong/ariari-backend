package com.ariari.ariari.domain.recruitment.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentInClubData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "모집 리스트 응답")
public class RecruitmentListRes {

    @Schema(description = "모집 데이터 리스트")
    private List<? extends RecruitmentData> recruitmentDataList;
    private PageInfo pageInfo;

    public static RecruitmentListRes fromList(List<Recruitment> recruitments, Member reqMember) {
        return new RecruitmentListRes(
                RecruitmentData.fromEntities(recruitments, reqMember),
                null
        );
    }

    public static RecruitmentListRes createInClubRes(List<Recruitment> recruitments) {
        return new RecruitmentListRes(
                RecruitmentInClubData.fromEntities(recruitments),
                null
        );
    }

    public static RecruitmentListRes fromPage(Page<Recruitment> page, Member reqMember) {
        return new RecruitmentListRes(
                RecruitmentData.fromEntities(page.getContent(), reqMember),
                PageInfo.fromPage(page)
        );
    }

    public static RecruitmentListRes fromBookmarkPage(Page<RecruitmentBookmark> page, Member reqMember) {
        List<RecruitmentBookmark> recruitmentBookmarks = page.getContent();
        List<Recruitment> recruitments = recruitmentBookmarks.stream().map(RecruitmentBookmark::getRecruitment).toList();

        return new RecruitmentListRes(
                RecruitmentData.fromEntities(recruitments, reqMember),
                PageInfo.fromPage(page)
        );
    }

}

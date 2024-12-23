package com.ariari.ariari.domain.recruitment.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class RecruitmentListRes {

    private List<RecruitmentData> contents;
    private PageInfo pageInfo;

    public static RecruitmentListRes fromList(List<Recruitment> recruitments, Member reqMember) {
        RecruitmentListRes recruitmentListRes = new RecruitmentListRes();
        recruitmentListRes.setContents(RecruitmentData.fromEntities(recruitments, reqMember));
        return recruitmentListRes;
    }

    public static RecruitmentListRes fromPage(Page<Recruitment> page, Member reqMember) {
        RecruitmentListRes recruitmentListRes = new RecruitmentListRes();
        recruitmentListRes.setContents(RecruitmentData.fromEntities(page.getContent(), reqMember));
        recruitmentListRes.setPageInfo(PageInfo.fromPage(page));
        return recruitmentListRes;
    }

}

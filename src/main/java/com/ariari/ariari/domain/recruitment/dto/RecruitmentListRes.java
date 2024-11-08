package com.ariari.ariari.domain.recruitment.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.recruitment.Recruitment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import software.amazon.awssdk.awscore.presigner.PresignedRequest;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitmentListRes {

    private List<RecruitmentData> contents;
    private PageInfo pageInfo;

    public static RecruitmentListRes fromList(List<Recruitment> recruitments) {
        RecruitmentListRes recruitmentListRes = new RecruitmentListRes();
        recruitmentListRes.setContents(RecruitmentData.fromEntities(recruitments));
        return recruitmentListRes;
    }

    public static RecruitmentListRes fromPage(Page<Recruitment> page) {
        RecruitmentListRes recruitmentListRes = new RecruitmentListRes();
        recruitmentListRes.setContents(RecruitmentData.fromEntities(page.getContent()));
        recruitmentListRes.setPageInfo(PageInfo.fromPage(page));
        return recruitmentListRes;
    }

}

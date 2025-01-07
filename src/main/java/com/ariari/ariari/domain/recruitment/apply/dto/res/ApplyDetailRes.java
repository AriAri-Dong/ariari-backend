package com.ariari.ariari.domain.recruitment.apply.dto.res;

import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.dto.ApplyAnswerData;
import com.ariari.ariari.domain.recruitment.apply.dto.ApplyData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyDetailRes {

    private ApplyData applyData;
    private List<ApplyAnswerData> applyAnswerDataList;

    private String fileUri;
    private String portfolioUrl;

    public static ApplyDetailRes fromEntity(Apply apply) {
        return new ApplyDetailRes(
                ApplyData.fromEntity(apply),
                ApplyAnswerData.fromEntities(apply.getApplyAnswers()),
                apply.getFileUri(),
                apply.getPortfolioUrl()
        );
    }

}

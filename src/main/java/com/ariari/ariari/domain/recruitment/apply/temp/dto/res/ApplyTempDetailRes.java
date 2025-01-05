package com.ariari.ariari.domain.recruitment.apply.temp.dto.res;

import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.ApplyAnswerTempData;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.ApplyTempData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyTempDetailRes {

    private ApplyTempData applyTempData;
    private List<ApplyAnswerTempData> applyAnswerTempDataList;

    private String fileUri;
    private String portfolioUrl;

    public static ApplyTempDetailRes fromEntity(ApplyTemp applyTemp) {
        return new ApplyTempDetailRes(
                ApplyTempData.fromEntity(applyTemp),
                ApplyAnswerTempData.fromEntities(applyTemp.getApplyAnswerTemps()),
                applyTemp.getFileUri(),
                applyTemp.getPortfolioUrl()
        );
    }

}

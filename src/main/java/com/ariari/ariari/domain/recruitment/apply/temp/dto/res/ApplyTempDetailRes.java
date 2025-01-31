package com.ariari.ariari.domain.recruitment.apply.temp.dto.res;

import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.ApplyAnswerTempData;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.ApplyTempData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "임시 지원서 상세 응답")
public class ApplyTempDetailRes {

    private ApplyTempData applyData;
    @Schema(description = "지원 형식에 대한 임시 응답 데이터 리스트")
    private List<ApplyAnswerTempData> applyAnswerDataList;

    @Schema(description = "포트폴리오 파일 URI")
    private String fileUri;
    @Schema(description = "포트폴리오 URI")
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

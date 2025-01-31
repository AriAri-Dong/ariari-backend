package com.ariari.ariari.domain.recruitment.apply.dto.res;

import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.dto.ApplyAnswerData;
import com.ariari.ariari.domain.recruitment.apply.dto.ApplyData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "지원서 상세 응답")
public class ApplyDetailRes {

    private ApplyData applyData;
    @Schema(description = "지원 형식 질문에 대한 응답 데이터 리스트")
    private List<ApplyAnswerData> applyAnswerDataList;

    @Schema(description = "포트폴리오 파일 URI")
    private String fileUri;
    @Schema(description = "포트폴리오 URI")
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

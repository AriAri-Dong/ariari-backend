package com.ariari.ariari.domain.recruitment.apply.temp.dto.res;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.dto.SpecialAnswerList;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.dto.ApplyAnswerTempData;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.ApplyTempData;
import com.ariari.ariari.domain.recruitment.applyform.dto.SpecialQuestionList;
import com.ariari.ariari.domain.recruitment.recruitment.dto.RecruitmentData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "임시 지원서 상세 응답")
public class ApplyTempDetailRes {

    private ApplyTempData applyData;
    private ClubData clubData;
    private RecruitmentData recruitmentData;

    @Schema(description = "지원 형식에 대한 임시 응답 데이터 리스트")
    private List<ApplyAnswerTempData> applyAnswerDataList;

    @Schema(description = "포트폴리오 파일 URI")
    private String fileUri;
    @Schema(description = "포트폴리오 URI")
    private String portfolioUrl;

    private SpecialAnswerList specialAnswerList;

    public static ApplyTempDetailRes fromEntity(ApplyTemp applyTemp) {
        Recruitment recruitment = applyTemp.getRecruitment();
        Club club = recruitment.getClub();

        List<ApplyAnswerTemp> answerList = new ArrayList<>();
        SpecialAnswerList specialAnswerList = new SpecialAnswerList();

        for (ApplyAnswerTemp answer : applyTemp.getApplyAnswerTemps()) {
            try {
                Field field = SpecialAnswerList.class.getDeclaredField(answer.getApplyQuestion().getBody());
                field.setAccessible(true);
                field.set(specialAnswerList, answer.getBody());
            } catch (NoSuchFieldException e) {
                answerList.add(answer);
            } catch (IllegalAccessException ignored) {}
        }

        return new ApplyTempDetailRes(
                ApplyTempData.fromEntity(applyTemp),
                ClubData.fromEntity(club, null),
                RecruitmentData.fromEntity(recruitment, null),
                ApplyAnswerTempData.fromEntities(answerList),
                applyTemp.getFileUri(),
                applyTemp.getPortfolioUrl(),
                specialAnswerList
        );
    }

}

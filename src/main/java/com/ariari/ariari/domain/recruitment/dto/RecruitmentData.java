package com.ariari.ariari.domain.recruitment.dto;

import com.ariari.ariari.domain.recruitment.Recruitment;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RecruitmentData {

    public static RecruitmentData fromEntity(Recruitment recruitment) {
        return RecruitmentData.builder()
                .build();
    }

    public static List<RecruitmentData> fromEntities(List<Recruitment> recruitments) {
        List<RecruitmentData> recruitmentDataList = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            recruitmentDataList.add(fromEntity(recruitment));
        }
        return recruitmentDataList;
    }

}

package com.ariari.ariari.domain.recruitment.apply.temp.dto;

import com.ariari.ariari.domain.member.dto.MemberData;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApplyTempData {

    private Long id;
    private LocalDateTime createdDateTime;

    private MemberData memberData;
    private String recruitmentTitle;
    private String clubName;

    public static ApplyTempData fromEntity(ApplyTemp applyTemp) {
        return new ApplyTempData(
                applyTemp.getId(),
                applyTemp.getCreatedDateTime(),
                MemberData.fromEntity(applyTemp.getMember()),
                applyTemp.getRecruitment().getTitle(),
                applyTemp.getRecruitment().getClub().getName()
        );
    }

    public static List<ApplyTempData> fromEntities(List<ApplyTemp> applyTemps) {
        return applyTemps.stream().map(ApplyTempData::fromEntity).toList();
    }

}
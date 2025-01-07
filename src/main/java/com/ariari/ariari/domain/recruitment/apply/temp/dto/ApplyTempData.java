package com.ariari.ariari.domain.recruitment.apply.temp.dto;

import com.ariari.ariari.domain.member.dto.MemberData;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApplyTempData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private ApplyStatusType applyStatusType;
    private LocalDateTime createdDateTime;

    private MemberData memberData;
    private String recruitmentTitle;
    private String clubName;

    public static ApplyTempData fromEntity(ApplyTemp applyTemp) {
        return new ApplyTempData(
                applyTemp.getId(),
                applyTemp.getName(),
                null,
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

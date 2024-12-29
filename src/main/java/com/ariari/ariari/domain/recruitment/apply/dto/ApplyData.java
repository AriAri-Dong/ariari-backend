package com.ariari.ariari.domain.recruitment.apply.dto;

import com.ariari.ariari.domain.member.dto.MemberData;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.enums.ApplyType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApplyData {

    private Long id;
    private ApplyStatusType applyStatusType;
    private LocalDateTime createdDateTime;

    private MemberData memberData;
    private String recruitmentTitle;
    private String clubName;

    private ApplyType applyType;

    public static ApplyData fromEntity(Apply apply) {
        return new ApplyData(
                apply.getId(),
                apply.getApplyStatusType(),
                apply.getCreatedDateTime(),
                MemberData.fromEntity(apply.getMember()),
                apply.getRecruitment().getTitle(),
                apply.getRecruitment().getClub().getName(),
                ApplyType.APPLY
        );
    }

    public static List<ApplyData> fromEntities(List<Apply> applies) {
        return applies.stream().map(ApplyData::fromEntity).toList();
    }

    public static ApplyData fromEntity(ApplyTemp applyTemp) {
        return new ApplyData(
                applyTemp.getId(),
                null,
                applyTemp.getCreatedDateTime(),
                MemberData.fromEntity(applyTemp.getMember()),
                applyTemp.getRecruitment().getTitle(),
                applyTemp.getRecruitment().getClub().getName(),
                ApplyType.APPLY_TEMP
        );
    }

    public static List<ApplyData> fromApplyTemps(List<ApplyTemp> applyTemps) {
        return applyTemps.stream().map(ApplyData::fromEntity).toList();
    }

}

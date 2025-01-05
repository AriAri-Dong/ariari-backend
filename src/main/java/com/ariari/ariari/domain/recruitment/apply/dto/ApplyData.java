package com.ariari.ariari.domain.recruitment.apply.dto;

import com.ariari.ariari.domain.member.dto.MemberData;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApplyData {

    private Long id;
    private String name;
    private ApplyStatusType applyStatusType;
    private LocalDateTime createdDateTime;

    private MemberData memberData;
    private String recruitmentTitle;
    private String clubName;

    public static ApplyData fromEntity(Apply apply) {
        return new ApplyData(
                apply.getId(),
                apply.getName(),
                apply.getApplyStatusType(),
                apply.getCreatedDateTime(),
                MemberData.fromEntity(apply.getMember()),
                apply.getRecruitment().getTitle(),
                apply.getRecruitment().getClub().getName()
        );
    }

    public static List<ApplyData> fromEntities(List<Apply> applies) {
        return applies.stream().map(ApplyData::fromEntity).toList();
    }

}

package com.ariari.ariari.domain.recruitment.apply.dto;

import com.ariari.ariari.domain.member.dto.MemberData;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.answer.dto.ApplyAnswerData;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ApplyData {

    private Long id;
    private ApplyStatusType applyStatusType;
    private LocalDateTime createdDateTime;

    private MemberData memberData;

    public static ApplyData fromEntity(Apply apply) {
        return new ApplyData(
                apply.getId(),
                apply.getApplyStatusType(),
                apply.getCreatedDateTime(),
                MemberData.fromEntity(apply.getMember())
        );
    }

}

package com.ariari.ariari.domain.recruitment.applyform.dto.res;

import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.dto.ApplyFormData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "지원 형식 응답")
public class ApplyFormRes {

    private ApplyFormData applyFormData;

    public static ApplyFormRes fromEntity(ApplyForm applyForm) {
        return new ApplyFormRes(
                ApplyFormData.fromEntity(applyForm)
        );
    }

}

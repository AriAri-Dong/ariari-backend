package com.ariari.ariari.domain.recruitment.applyform.dto;

import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplyFormRes {

    private ApplyFormData applyFormData;

    public static ApplyFormRes fromEntity(ApplyForm applyForm) {
        return new ApplyFormRes(
                ApplyFormData.fromEntity(applyForm)
        );
    }

}

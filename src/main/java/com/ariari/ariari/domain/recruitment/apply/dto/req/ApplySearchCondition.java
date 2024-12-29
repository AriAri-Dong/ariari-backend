package com.ariari.ariari.domain.recruitment.apply.dto.req;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplySearchCondition {

    private Boolean isPendent;
    private String query;
    private LocalDate startDate;
    private LocalDate endDate;

}

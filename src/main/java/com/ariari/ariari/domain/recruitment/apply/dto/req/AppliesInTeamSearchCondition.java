package com.ariari.ariari.domain.recruitment.apply.dto.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppliesInTeamSearchCondition {

    private Boolean isPendent;
    private String query;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}

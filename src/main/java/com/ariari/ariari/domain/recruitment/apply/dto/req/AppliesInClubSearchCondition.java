package com.ariari.ariari.domain.recruitment.apply.dto.req;

import com.ariari.ariari.domain.recruitment.apply.exception.SearchAppliesInClubException;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppliesInClubSearchCondition {

    private Boolean isPendent;
    private String query;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public void validateCondition() {
        if ((startDateTime == null && endDateTime != null) || startDateTime != null && endDateTime == null) {
            throw new SearchAppliesInClubException();
        }
    }

}

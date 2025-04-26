package com.ariari.ariari.domain.recruitment.apply.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class AppliesInterviewReq {

    private List<Long> applyIds;
    private String interviewMessage;

}

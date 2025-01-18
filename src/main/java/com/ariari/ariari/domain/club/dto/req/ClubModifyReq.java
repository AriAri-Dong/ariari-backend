package com.ariari.ariari.domain.club.dto.req;

import com.ariari.ariari.domain.club.Club;
import lombok.Data;

@Data
public class ClubModifyReq {

    private String body;

    public void modifyEntity(Club club) {
        club.setBody(body);
    }

}

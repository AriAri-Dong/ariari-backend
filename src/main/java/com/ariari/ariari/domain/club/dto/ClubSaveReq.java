package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.domain.club.Club;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubSaveReq {

    private String name;

    public Club toEntity() {
        return Club.builder()
                .name(this.name)
                .build();
    }

}

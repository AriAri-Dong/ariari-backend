package com.ariari.ariari.domain.club.event.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.event.ClubEvent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClubEventSaveReq {

    private String title;
    private String body;
    private String location;
    private LocalDateTime eventDateTime;

    public ClubEvent toEntity(Club club) {
        return new ClubEvent(
                title,
                body,
                location,
                eventDateTime,
                club
        );
    }

    public void modifyEntity(ClubEvent clubEvent) {
        clubEvent.modify(
                title,
                body,
                location,
                eventDateTime
        );
    }

}

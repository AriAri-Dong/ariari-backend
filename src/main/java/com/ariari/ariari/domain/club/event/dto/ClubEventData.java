package com.ariari.ariari.domain.club.event.dto;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ClubEventData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String body;
    private String location;
    private LocalDateTime eventDateTime;

    private List<ClubMemberData> clubMemberDataList;
    private Long attendeeCount;

    public static ClubEventData fromEntity(ClubEvent clubEvent, List<ClubMember> clubMembers, Long attendeeCount) {
        return new ClubEventData(
                clubEvent.getId(),
                clubEvent.getTitle(),
                clubEvent.getBody(),
                clubEvent.getLocation(),
                clubEvent.getEventDateTime(),
                ClubMemberData.fromEntities(clubMembers),
                attendeeCount
        );
    }

    public static List<ClubEventData> fromEntities(List<ClubEvent> clubEvents, Map<ClubEvent, List<ClubMember>> clubMemberMap, Map<ClubEvent, Long> attendeeCountMap) {
        List<ClubEventData> clubEventDataList = new ArrayList<>();
        for (ClubEvent clubEvent : clubEvents) {
            clubEventDataList.add(fromEntity(
                    clubEvent,
                    clubMemberMap.get(clubEvent),
                    attendeeCountMap.get(clubEvent)));
        }
        return clubEventDataList;
    }

}

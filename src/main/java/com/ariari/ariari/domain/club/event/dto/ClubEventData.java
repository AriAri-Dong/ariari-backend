package com.ariari.ariari.domain.club.event.dto;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Schema(description = "동아리 일정 데이터")
public class ClubEventData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 일정 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 일정 제목", example = "아리아리 전체 정기 회의")
    private String title;
    @Schema(description = "동아리 일정 내용", example = "아리아리의 전체 정기 회의입니다. 불참은 없습니다.")
    private String body;
    @Schema(description = "동아리 일정 장소", example = "건대 카페온더 플랜 B1")
    private String location;
    @Schema(description = "동아리 일정 날짜", example = "2025-01-31T09:08:18.467Z")
    private LocalDateTime eventDateTime;

    @Schema(description = "출석한 동아리 회원 데이터 리스트")
    private List<ClubMemberData> clubMemberDataList;
    @Schema(description = "출석한 동아리 회원 수", example = "10")
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

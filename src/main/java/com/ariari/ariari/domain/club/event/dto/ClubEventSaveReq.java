package com.ariari.ariari.domain.club.event.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.event.ClubEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "동아리 일정 등록 형식")
public class ClubEventSaveReq {

    @Schema(description = "동아리 일정 제목", example = "아리아리 전체 정기 회의")
    private String title;
    @Schema(description = "동아리 일정 내용", example = "아리아리의 전체 정기 회의입니다. 불참은 없습니다.")
    private String body;
    @Schema(description = "동아리 일정 장소", example = "건대 카페온더 플랜 B1")
    private String location;
    @Schema(description = "동아리 일정 날짜", example = "2025-01-31T09:08:18.467Z")
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

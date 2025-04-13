package com.ariari.ariari.domain.club.club.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.school.dto.SchoolData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "내 동아리 데이터")
public class MyClubData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 이름", example = "아리아리")
    private String name;
    @Schema(description = "동아리 프로필 이미지 URI", example = "")
    private String profileUri;

    private LocalDateTime clubNoticeCreatedDt;
    private LocalDateTime clubActivityCreatedDt;
//    private LocalDateTime eventCreatedDt;

    public MyClubData(Club club, ClubNotice clubNotice, ClubActivity clubActivity) {
        this.id = club.getId();
        this.name = club.getName();
        this.profileUri = club.getProfileUri();
        this.clubNoticeCreatedDt = clubNotice == null ? null : clubNotice.getCreatedDateTime();
        this.clubActivityCreatedDt = clubActivity == null ? null : clubActivity.getCreatedDateTime();
    }

}

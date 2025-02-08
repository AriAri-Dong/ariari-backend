package com.ariari.ariari.domain.club.club.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.club.enums.ParticipantType;
import com.ariari.ariari.domain.school.School;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "동아리 등록 형식")
public class ClubSaveReq {

    @Schema(description = "동아리 이름", example = "아리아리")
    private String name;
    @Schema(description = "동아리 한 줄 소개", example = "아리아리는 동아리 커뮤니티 서비스를 개발하는 동아리입니다.")
    private String body;
    @Schema(description = "동아리 소속 타입", example = "EXTERNAL")
    private ClubAffiliationType affiliationType;
    @Schema(description = "동아리 카테고리 타입", example = "STARTUP")
    private ClubCategoryType categoryType;
    @Schema(description = "동아리 지역 타입", example = "SEOUL_GYEONGGI")
    private ClubRegionType regionType;
    @Schema(description = "동아리 참여 대상 타입", example = "OFFICE_WORKER")
    private ParticipantType participantType;

    public Club toEntity(School school) {
        return new Club(name,
                body,
                categoryType,
                regionType,
                participantType,
                school);
    }

}

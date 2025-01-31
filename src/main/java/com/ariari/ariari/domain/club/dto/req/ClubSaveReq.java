package com.ariari.ariari.domain.club.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.school.School;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "동아리 등록 형식")
public class ClubSaveReq {

    private String name;
    private String body;
    private ClubAffiliationType affiliationType;
    private ClubCategoryType categoryType;
    private ClubRegionType regionType;
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

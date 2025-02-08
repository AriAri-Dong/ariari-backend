package com.ariari.ariari.domain.club.club.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동아리 카테고리 타입")
public enum ClubCategoryType {

    CULTURE,
    VOLUNTEER,
    STUDY,
    STARTUP,
    EMPLOYMENT,
    SPORTS,
    AMITY,
    ETC

}

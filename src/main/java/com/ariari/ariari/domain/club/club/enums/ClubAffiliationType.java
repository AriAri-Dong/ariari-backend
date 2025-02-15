package com.ariari.ariari.domain.club.club.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동아리 소속 타입 (INTERNAL : 교내, EXTERNAL : 교외)")
public enum ClubAffiliationType {

    INTERNAL,
    EXTERNAL

}

package com.ariari.ariari.domain.club.clubmember.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동아리 회원 상태 타입 (ACTIVE : 활성화, INACTIVE : 비활성화, WITHDRAWN : 탈퇴")
public enum ClubMemberStatusType {

    ACTIVE,
    INACTIVE,
    WITHDRAWN

}

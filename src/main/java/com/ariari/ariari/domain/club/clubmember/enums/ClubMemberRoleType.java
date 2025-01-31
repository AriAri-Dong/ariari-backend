package com.ariari.ariari.domain.club.clubmember.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "동아리 회원 권한 타입 (GENERAL : 일반 동아리 회원, MANAGER : 동아리 관리자, ADMIN : 동아리 최고 관리자")
public enum ClubMemberRoleType {

    GENERAL,
    MANAGER,
    ADMIN

}

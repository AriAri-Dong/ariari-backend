package com.ariari.ariari.domain.system.dto;

import com.ariari.ariari.domain.club.alarm.ClubAlarm;
import com.ariari.ariari.domain.club.alarm.enums.ClubAlarmType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.system.SystemNotice;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "서비스 공지사항 데이터 ")
public class SystemNoticeData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "서비스 공지사항 id", example = "673012345142938986")
    private final Long id;
    @Schema(description = "서비스 공지사항 제목", example = "아리아리에서 개발한 서비스의 배포 시작")
    private final String title;
    @Schema(description = "서비스 공지사항 내용", example = "아리아리에서 개발한 동아리 커뮤니티 서비스의 배포가 시작되었습니다!")
    private final String body;
    @Schema(description = "서비스 공지사항 고정 여부", example = "true")
    private final Boolean isFixed;
    @Schema(description = "서비스 공지사항 생성 날짜/시간", example = "2025-01-31T09:08:18.467Z")
    private final LocalDateTime createdDateTime;

    private SystemNoticeData(Long id, String title, String body, Boolean isFixed, LocalDateTime createdDateTime) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.isFixed = isFixed;
        this.createdDateTime = createdDateTime;
    }

    public static SystemNoticeData fromEntity(SystemNotice systemNotice){
        return new SystemNoticeData(systemNotice.getId(),
                systemNotice.getTitle(),
                systemNotice.getBody(),
                systemNotice.getIsFixed(),
                systemNotice.getCreatedDateTime());
    }

}





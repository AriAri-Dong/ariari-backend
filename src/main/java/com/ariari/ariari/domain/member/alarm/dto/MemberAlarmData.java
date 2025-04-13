package com.ariari.ariari.domain.member.alarm.dto;

import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "회원 알림 데이터")
public class MemberAlarmData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "회원 알림 id", example = "673012345142938986")
    private Long id;

    @Schema(description = "알림 제목", example = "임시저장된 지원서 모집마감임박(D-1)")
    private String title;

    @Schema(description = "연결 uri", example = "/clubs/{clubId}")
    private String uri;

    @Schema(description = "알림 내용", example = "true")
    private Boolean isChecked;


    @Schema(description = "알림 생성 날짜/시간", example = "2025-01-31T09:08:18.467Z")
    private LocalDateTime createdDateTime;

    @Builder
    private MemberAlarmData(Long id, String title, String uri, Boolean isChecked, LocalDateTime createdDateTime) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.isChecked = isChecked != null ? isChecked : Boolean.FALSE; // 기본값 처리
        this.createdDateTime = createdDateTime;
    }

    public static MemberAlarmData fromEntity(MemberAlarm memberAlarm) {
        return MemberAlarmData.builder()
                .id(memberAlarm.getId())
                .title(memberAlarm.getTitle())
                .uri(memberAlarm.getUri())
                .isChecked(memberAlarm.getIsChecked())
                .createdDateTime(memberAlarm.getCreatedDateTime())
                .build();
    }
}



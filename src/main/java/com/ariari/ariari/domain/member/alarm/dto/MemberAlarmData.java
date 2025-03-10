package com.ariari.ariari.domain.member.alarm.dto;

import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "회원 알림 데이터 (memberData 는 작성한 회원 데이터입니다. clubAnswerData 는 질문에 대한 답변 데이터입니다.")
public class MemberAlarmData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "회원 알림 id", example = "673012345142938986")
    private Long id;

    @Schema(description = "알림 제목", example = "임시저장된 지원서 모집마감임박(D-1)")
    private String title;

    @Schema(description = "알림 내용", example = "임시저장된 지원서 모집마감임박(D-1)")
    private String body;

    @Schema(description = "추가 내용", example = "임시저장된 지원서 모집마감임박(D-1)")
    private String extraBody;

    @Schema(description = "연결 uri", example = "임시저장된 지원서 모집마감임박(D-1)")
    private String uri;

    @Schema(description = "알림 내용", example = "true")
    private Boolean isChecked;

    @Schema(description = "알림 종류", example = "club")
    private MemberAlarmType memberAlarmType;

    @Schema(description = "알림 생성 날짜/시간", example = "2025-01-31T09:08:18.467Z")
    private LocalDateTime createdDateTime;

    @Builder
    private MemberAlarmData(Long id, String title, String body, String extraBody, MemberAlarmType memberAlarmType, String uri, Boolean isChecked, LocalDateTime createdDateTime) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.extraBody = extraBody;
        this.memberAlarmType = memberAlarmType;
        this.uri = uri;
        this.isChecked = isChecked != null ? isChecked : Boolean.FALSE; // 기본값 처리
        this.createdDateTime = createdDateTime;
    }
}



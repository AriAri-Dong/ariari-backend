package com.ariari.ariari.domain.club.passreview.dto.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PassReviewRes {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "합격후기 id", example = "id값")
    private String id;
    @Schema(description = "합격후기 제목", example = "제목")
    private String title;
    @Schema(description = "작성일자", example = "localDateTime, ...")
    private LocalDateTime createdDateTime;
    @Schema(description = "서류 관련 문항 개수", example = "")
    private int documentNoteCount;
    @Schema(description = "면접 전형 관련 개수", example = "질문과 문항만 담겨 있음")
    private int interviewNoteCount;
}

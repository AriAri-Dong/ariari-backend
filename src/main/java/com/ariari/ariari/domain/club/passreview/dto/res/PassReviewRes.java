package com.ariari.ariari.domain.club.passreview.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PassReviewRes {
    @Schema(description = "합격후기 id", example = "id값")
    private Long id;
    @Schema(description = "합격후기 제목", example = "제목")
    private String title;
    @Schema(description = "작성일자", example = "localDateTime, ...")
    private LocalDateTime createdDateTime;
    @Schema(description = "작성자 본인인지 여부", example = "작성자는 본인이 열람 가능해야함")
    private boolean isCreator;
    @Schema(description = "접근 가능 여부", example = "과거에 포인트를 사용하여 열람한 적이 있다면 편하게 열람 가능")
    private boolean wasAccessed;
    @Schema(description = "서류 관련 문항 개수", example = "")
    private int documentNoteCount;
    @Schema(description = "면접 전형 관련 개수", example = "질문과 문항만 담겨 있음")
    private int interviewNoteCount;
}

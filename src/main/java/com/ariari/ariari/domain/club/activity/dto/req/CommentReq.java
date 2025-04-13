package com.ariari.ariari.domain.club.activity.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentReq {
    @Schema(description = "댓글 내용", example = "댓글 내용")
    @NotEmpty(message = "빈 값일 수 없습니다.")
    private String body;
}

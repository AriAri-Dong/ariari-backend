package com.ariari.ariari.domain.recruitment.note.dto;

import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "모집 추가 항목 형식")
public class RecruitmentNoteSaveReq {

    @Schema(description = "질문", example = "활동 장소")
    private String question;
    @Schema(description = "응답", example = "강남 인근 스터디룸")
    private String answer;

    public RecruitmentNote toEntity() {
        return new RecruitmentNote(
                this.question,
                this.answer);
    }

}

package com.ariari.ariari.domain.recruitment.note.dto;

import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "모집 추가 항목 데이터")
public class RecruitmentNoteData {

    @Schema(description = "질문", example = "활동 장소")
    private String question;
    @Schema(description = "응답", example = "강남 인근 스터디룸")
    private String answer;

    public static RecruitmentNoteData fromEntity(RecruitmentNote recruitmentNote) {
        return new RecruitmentNoteData(
                recruitmentNote.getQuestion(),
                recruitmentNote.getAnswer()
        );
    }

    public static List<RecruitmentNoteData> fromEntities(List<RecruitmentNote> recruitmentNotes) {
        return recruitmentNotes.stream().map(RecruitmentNoteData::fromEntity).toList();
    }

}

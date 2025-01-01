package com.ariari.ariari.domain.club.passreview.dto;

import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.club.passreview.note.QPassReviewNote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PassReviewNoteData {
    @Schema(description = "노트 제목", example = "제목")
    private String title;

    @Schema(description = "노트 본문", example = "본문")
    private String body;

    public static List<PassReviewNoteData> fromEntities(List<PassReviewNote> passReviewNotes) {
        List<PassReviewNoteData> passReviewNoteDataList = new ArrayList<>();
        for (PassReviewNote passReviewNote : passReviewNotes) {
            passReviewNoteDataList.add(PassReviewNoteData.fromEntity(passReviewNote));
        }
        return passReviewNoteDataList;
    } // 가독성을 위해 for문

    public static PassReviewNoteData fromEntity(PassReviewNote passReviewNote) {
        return PassReviewNoteData.builder()
                .title(passReviewNote.getTitle())
                .body(passReviewNote.getBody())
                .build();
    }
}

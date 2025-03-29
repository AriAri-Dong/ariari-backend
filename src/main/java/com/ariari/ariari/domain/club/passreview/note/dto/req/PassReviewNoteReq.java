package com.ariari.ariari.domain.club.passreview.note.dto.req;

import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PassReviewNoteReq {

    @Schema(description = "문항", example = "문항")
    private String title;
    @Schema(description = "내용", example = "내용")
    private String body;
    @Schema(description = "타입 서류문항이라면 DOCUMENT, 면접문항이라면 INTERVIEW ", example = "INTERVIEW")
    private NoteType noteType;

    public PassReviewNote toEntity() {
        return new PassReviewNote(
                noteType,
                title,
                body
        );
    }

}

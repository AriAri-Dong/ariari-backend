package com.ariari.ariari.domain.club.passreview.note.dto.req;

import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import lombok.Data;

@Data
public class PassReviewNoteReq {

    private String title;
    private String body;
    private NoteType noteType;

    public PassReviewNote toEntity() {
        return new PassReviewNote(
                noteType,
                title,
                body
        );
    }

}

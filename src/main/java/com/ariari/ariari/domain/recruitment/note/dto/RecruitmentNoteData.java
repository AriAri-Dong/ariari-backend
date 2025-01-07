package com.ariari.ariari.domain.recruitment.note.dto;

import com.ariari.ariari.domain.recruitment.dto.RecruitmentData;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecruitmentNoteData {

    private String question;
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

package com.ariari.ariari.domain.recruitment.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import com.ariari.ariari.domain.recruitment.note.dto.RecruitmentNoteSaveReq;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitmentSaveReq {

    private String title;
    private String body;
    private ProcedureType procedureType;
    private LocalDateTime endDateTime;
    private Integer limits;

    private List<RecruitmentNoteSaveReq> recruitmentNotes = new ArrayList<>();

    public Recruitment toEntity(Club club, ApplyForm applyForm) {
        List<RecruitmentNote> recruitmentNoteList = recruitmentNotes.stream().map(RecruitmentNoteSaveReq::toEntity).toList();

        return new Recruitment(
                title,
                body,
                procedureType,
                limits,
                endDateTime,
                club,
                applyForm,
                recruitmentNoteList
        );
    }

}

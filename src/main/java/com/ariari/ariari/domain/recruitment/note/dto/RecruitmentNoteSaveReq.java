package com.ariari.ariari.domain.recruitment.note.dto;

import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import lombok.Data;

@Data
public class RecruitmentNoteSaveReq {

    private String question;
    private String answer;

    public RecruitmentNote toEntity() {
        return new RecruitmentNote(
                this.question,
                this.answer);
    }

}

package com.ariari.ariari.domain.recruitment.recruitment.dto.req;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import com.ariari.ariari.domain.recruitment.note.dto.RecruitmentNoteSaveReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "모집 등록 형식")
public class RecruitmentSaveReq {

    @Schema(description = "모집 제목", example = "아리아리 3기 모집")
    private String title;
    @Schema(description = "모집 활동 내용", example = "동아리 커뮤니티 서비스를 개발합니다.")
    private String body;
    @Schema(description = "모집 절차 타입", example = "DOCUMENT")
    private ProcedureType procedureType;
    @Schema(description = "모집 인원", example = "30")
    private Integer limits;
    @Schema(description = "모집 시작 날짜/시간", example = "2025-01-15T09:08:18.467Z")
    private LocalDateTime startDateTime;
    @Schema(description = "모집 종료 날짜/시간", example = "2025-03-15T09:08:18.467Z")
    private LocalDateTime endDateTime;

    @Schema(description = "모집 추가 항목 리스트")
    private List<RecruitmentNoteSaveReq> recruitmentNotes = new ArrayList<>();

    public Recruitment toEntity(Club club, ApplyForm applyForm) {
        List<RecruitmentNote> recruitmentNoteList = recruitmentNotes.stream().map(RecruitmentNoteSaveReq::toEntity).toList();

        Recruitment recruitment = new Recruitment(
                title,
                body,
                procedureType,
                limits,
                startDateTime,
                endDateTime,
                club,
                applyForm,
                recruitmentNoteList
        );

        for (RecruitmentNote recruitmentNote : recruitmentNoteList) {
            recruitmentNote.setRecruitment(recruitment);
        }

        return recruitment;
    }

}

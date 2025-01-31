package com.ariari.ariari.domain.recruitment.dto;

import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.note.dto.RecruitmentNoteData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecruitmentInClubData extends RecruitmentData {

    private List<RecruitmentNoteData> recruitmentNoteDataList;

    public static List<RecruitmentInClubData> fromEntities(List<Recruitment> recruitments) {
        return recruitments.stream().map(RecruitmentInClubData::fromEntity).toList();
    }

    private static RecruitmentInClubData fromEntity(Recruitment recruitment) {
        return new RecruitmentInClubData(
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getBody(),
                recruitment.getPosterUri(),
                recruitment.getProcedureType(),
                recruitment.getLimits(),
                recruitment.getCreatedDateTime(),
                recruitment.getStartDateTime(),
                recruitment.getEndDateTime(),
                recruitment.getIsActivated(),
                null,
                RecruitmentNoteData.fromEntities(recruitment.getRecruitmentNotes())
        );
    }

    public RecruitmentInClubData(Long id, String title, String body, String posterUri, ProcedureType procedureType, Integer limits, LocalDateTime createdDateTime, LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean isActivated, Boolean isMyBookmark, List<RecruitmentNoteData> recruitmentNoteDataList) {
        super(id, title, body, posterUri, procedureType, limits, createdDateTime, startDateTime, endDateTime, isActivated, isMyBookmark);
        this.recruitmentNoteDataList = recruitmentNoteDataList;
    }

}

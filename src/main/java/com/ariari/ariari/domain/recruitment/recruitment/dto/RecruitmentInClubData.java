package com.ariari.ariari.domain.recruitment.recruitment.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.club.enums.ParticipantType;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.note.dto.RecruitmentNoteData;
import com.ariari.ariari.domain.recruitment.recruitment.enums.RecruitmentStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "동아리의 모집 리스트 조회 전용 모집 데이터")
public class RecruitmentInClubData extends RecruitmentData {

    @Schema(description = "모집 추가 항목 데이터 리스트")
    private List<RecruitmentNoteData> recruitmentNoteDataList;

    public static List<RecruitmentInClubData> fromEntities(List<Recruitment> recruitments) {
        return recruitments.stream().map(RecruitmentInClubData::fromEntity).toList();
    }

    private static RecruitmentInClubData fromEntity(Recruitment recruitment) {
        Club club = recruitment.getClub();
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
                club.getId(),
                club.getSchool() == null ? ClubAffiliationType.EXTERNAL : ClubAffiliationType.INTERNAL,
                club.getClubCategoryType(),
                club.getClubRegionType(),
                club.getParticipantType(),
                recruitment.getRecruitmentStatusType(),
                null,
                RecruitmentNoteData.fromEntities(recruitment.getRecruitmentNotes())
        );
    }

    public RecruitmentInClubData(Long id, String title, String body, String posterUri, ProcedureType procedureType, Integer limits, LocalDateTime createdDateTime, LocalDateTime startDateTime, LocalDateTime endDateTime, Long clubId, ClubAffiliationType clubAffiliationType, ClubCategoryType clubCategoryType, ClubRegionType clubRegionType, ParticipantType participantType, RecruitmentStatusType recruitmentStatusType, Boolean isMyBookmark, List<RecruitmentNoteData> recruitmentNoteDataList) {
        super(id, title, body, posterUri, procedureType, limits, createdDateTime, startDateTime, endDateTime, clubId, clubAffiliationType, clubCategoryType, clubRegionType, participantType, recruitmentStatusType, isMyBookmark);
        this.recruitmentNoteDataList = recruitmentNoteDataList;
    }

}

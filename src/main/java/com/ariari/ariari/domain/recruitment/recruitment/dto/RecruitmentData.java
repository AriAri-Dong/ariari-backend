package com.ariari.ariari.domain.recruitment.recruitment.dto;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.recruitment.enums.RecruitmentStatusType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "모집 데이터")
public class RecruitmentData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "모집 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "모집 제목", example = "아리아리 3기 모집")
    private String title;
    @Schema(description = "모집 활동 내용", example = "동아리 커뮤니티 서비스를 개발합니다.")
    private String body;
    @Schema(description = "모집 poster URI")
    private String posterUri;
    @Schema(description = "모집 절차 타입", example = "DOCUMENT")
    private ProcedureType procedureType;
    @Schema(description = "모집 인원", example = "30")
    private Integer limits;
    @Schema(description = "모집 생성 날짜/시간", example = "2025-03-15T09:08:18.467Z")
    private LocalDateTime createdDateTime;
    @Schema(description = "모집 시작 날짜/시간", example = "2025-01-15T09:08:18.467Z")
    private LocalDateTime startDateTime;
    @Schema(description = "모집 종료 날짜/시간", example = "2025-03-15T09:08:18.467Z")
    private LocalDateTime endDateTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "모집이 속한 동아리 id", example = "673012345142938986")
    private Long clubId;

    @Schema(description = "모집 상태 (예정, 모집중, 모집 마감)", example = "OPEN")
    private RecruitmentStatusType recruitmentStatusType;
    @Schema(description = "내가 북마크한 모집인지 여부", example = "true")
    private Boolean isMyBookmark;

    public static RecruitmentData fromEntity(Recruitment recruitment, Member reqMember) {
        return new RecruitmentData(
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getBody(),
                recruitment.getPosterUri(),
                recruitment.getProcedureType(),
                recruitment.getLimits(),
                recruitment.getCreatedDateTime(),
                recruitment.getStartDateTime(),
                recruitment.getEndDateTime(),
                recruitment.getClub().getId(),
                recruitment.getRecruitmentStatusType(),
                getMyBookmarkRecruitments(reqMember).contains(recruitment)
        );
    }

    public static List<RecruitmentData> fromEntities(List<Recruitment> recruitments, Member reqMember) {
        Set<Recruitment> myBookmarkRecruitments = getMyBookmarkRecruitments(reqMember);

        List<RecruitmentData> recruitmentDataList = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            recruitmentDataList.add(fromEntity(recruitment, myBookmarkRecruitments));
        }
        return recruitmentDataList;
    }

    private static RecruitmentData fromEntity(Recruitment recruitment, Set<Recruitment> myBookmarkRecruitments) {
        return new RecruitmentData(
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getBody(),
                recruitment.getPosterUri(),
                recruitment.getProcedureType(),
                recruitment.getLimits(),
                recruitment.getCreatedDateTime(),
                recruitment.getStartDateTime(),
                recruitment.getEndDateTime(),
                recruitment.getClub().getId(),
                recruitment.getRecruitmentStatusType(),
                myBookmarkRecruitments.contains(recruitment)
        );
    }

    /**
     * 요청 회원의 북마크 모집 집합 반환
     */
    private static Set<Recruitment> getMyBookmarkRecruitments(Member reqMember) {
        if (reqMember == null) {
            return new HashSet<>();
        } else {
            List<RecruitmentBookmark> recruitmentBookmarks = reqMember.getRecruitmentBookmarks();
            return recruitmentBookmarks.stream().map(RecruitmentBookmark::getRecruitment).collect(Collectors.toSet());
        }
    }

}

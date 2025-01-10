package com.ariari.ariari.domain.recruitment.dto;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.enums.ProcedureType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.keyvalue.repository.query.PredicateQueryCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RecruitmentData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String body;
    private String posterUri;
    private ProcedureType procedureType;
    private Integer limits;
    private LocalDateTime createdDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Boolean isActivated;
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
                recruitment.getIsActivated(),
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
                recruitment.getIsActivated(),
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

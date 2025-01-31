package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.dto.SchoolData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Schema(description = "동아리 데이터")
public class ClubData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 이름", example = "아리아리")
    private String name;
    @Schema(description = "동아리 프로필 이미지 URI", example = "")
    private String profileUri;
    @Schema(description = "동아리 한 줄 소개", example = "아리아리는 동아리 커뮤니티 서비스를 개발하는 동아리입니다.")
    private String body;
    @Schema(description = "동아리 배너 이미지 URI", example = "")
    private String bannerUri;
    @Schema(description = "동아리 카테고리 타입", example = "STARTUP")
    private ClubCategoryType clubCategoryType;
    @Schema(description = "동아리 지역 타입", example = "SEOUL_GYEONGGI")
    private ClubRegionType clubRegionType;
    @Schema(description = "동아리 참여 대상 타입", example = "OFFICE_WORKER")
    private ParticipantType participantType;

    @Schema(description = "내가 북마크한 동아리인지 여부")
    private Boolean isMyBookmark = false;

    private SchoolData schoolData;

    public static ClubData fromEntity(Club club, Member reqMember) {
        Set<Club> myBookmarkClubs = getMyBookmarkClubs(reqMember);
        return fromEntity(club, myBookmarkClubs, reqMember);
    }

    public static List<ClubData> fromEntities(List<Club> clubs, Member reqMember) {
        // 북마크 클럽 집합
        Set<Club> myBookmarkClubs = getMyBookmarkClubs(reqMember);

        List<ClubData> clubDataList = new ArrayList<>();
        for (Club club : clubs) {
            clubDataList.add(fromEntity(club, myBookmarkClubs, reqMember));
        }
         return clubDataList;
    }

    private static ClubData fromEntity(Club club, Set<Club> myBookmarkClubs, Member reqMember) {
        SchoolData schoolData = null;
        if (reqMember != null && reqMember.getSchool() != null) {
            schoolData = SchoolData.fromEntity(reqMember.getSchool());
        }
        return new ClubData(
                club.getId(),
                club.getName(),
                club.getProfileUri(),
                club.getBody(),
                club.getBannerUri(),
                club.getClubCategoryType(),
                club.getClubRegionType(),
                club.getParticipantType(),
                myBookmarkClubs.contains(club),
                schoolData
        );
    }

    /**
     * 요청 회원의 북마크 클럽 집합 반환
     */
    private static Set<Club> getMyBookmarkClubs(Member reqMember) {
        if (reqMember == null) {
            return new HashSet<>();
        } else {
            List<ClubBookmark> clubBookmarks = reqMember.getClubBookmarks();
            return clubBookmarks.stream().map(ClubBookmark::getClub).collect(Collectors.toSet());
        }
    }
}

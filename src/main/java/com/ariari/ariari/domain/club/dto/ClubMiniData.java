package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.enums.RegionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ClubMiniData {

    private Long id;
    private String name;
    private String imagePath;
    private String introduction;

    public static ClubMiniData fromEntity(Club club) {
        return ClubMiniData.builder()
                .id(club.getId())
                .name(club.getName())
                .imagePath(club.getImagePath())
                .introduction(club.getIntroduction())
                .build();
    }

    public static List<ClubMiniData> fromEntities(List<Club> clubs) {
        List<ClubMiniData> clubMiniDataList = new ArrayList<>();
        for (Club club : clubs) {
            clubMiniDataList.add(fromEntity(club));
        }
        return clubMiniDataList;
    }

}

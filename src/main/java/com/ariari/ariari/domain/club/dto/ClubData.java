package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.domain.club.Club;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ClubData {

    private Long id;
    private String name;
    private String imagePath;
    private String introduction;

    public static ClubData fromEntity(Club club) {
        return ClubData.builder()
                .id(club.getId())
                .name(club.getName())
                .imagePath(club.getImagePath())
                .introduction(club.getIntroduction())
                .build();
    }

    public static List<ClubData> fromEntities(List<Club> clubs) {
        List<ClubData> clubDataList = new ArrayList<>();
        for (Club club : clubs) {
            clubDataList.add(fromEntity(club));
        }
        return clubDataList;
    }

}

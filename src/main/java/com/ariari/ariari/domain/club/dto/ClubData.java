package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.clubmember.ClubMember;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static ClubData fromClubMember(ClubMember clubMember) {
        Club club = clubMember.getClub();
        return ClubData.fromEntity(club);
    }

    public static List<ClubData> fromClubMembers(List<ClubMember> clubMembers) {
        List<Club> clubs = clubMembers.stream().map(ClubMember::getClub).toList();
        return fromEntities(clubs);
    }

}

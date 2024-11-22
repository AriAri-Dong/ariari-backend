package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubbookmark.ClubBookmark;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class ClubData {

    private Long id;
    private String name;
    private String imagePath;
    private String introduction;

    private Boolean isMyBookmark = false;

    public static ClubData fromEntity(Club club, Member reqMember) {
        Set<Club> myBookmarkClubs = getMyBookmarkClubs(reqMember);
        return fromEntity(club, myBookmarkClubs);
    }

    public static List<ClubData> fromEntities(List<Club> clubs, Member reqMember) {
        // 북마크 클럽 집합
        Set<Club> myBookmarkClubs = getMyBookmarkClubs(reqMember);

        List<ClubData> clubDataList = new ArrayList<>();
        for (Club club : clubs) {
            clubDataList.add(fromEntity(club, myBookmarkClubs));
        }
         return clubDataList;
    }

    private static ClubData fromEntity(Club club, Set<Club> myBookmarkClubs) {
        return ClubData.builder()
                .id(club.getId())
                .name(club.getName())
                .imagePath(club.getImagePath())
                .introduction(club.getIntroduction())
                .isMyBookmark(myBookmarkClubs.contains(club))
                .build();
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

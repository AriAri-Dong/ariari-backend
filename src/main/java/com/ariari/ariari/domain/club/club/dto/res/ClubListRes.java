package com.ariari.ariari.domain.club.club.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.club.club.dto.ClubData;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ClubListRes {

    @Schema(description = "동아리 데이터 리스트")
    private List<ClubData> clubDataList;
    private PageInfo pageInfo;

    public static ClubListRes fromPage(Page<ClubData> page, Member reqMember) {
        if (reqMember != null) {
            Set<Long> clubBookmarkIds = reqMember.getClubBookmarks().stream().map(cb -> cb.getClub().getId()).collect(Collectors.toSet());
            for (ClubData ClubData : page.getContent()) {
                if (clubBookmarkIds.contains(ClubData.getId())) {
                    ClubData.setIsMyBookmark(true);
                }
            }
        }

        return new ClubListRes(
                page.getContent(),
                PageInfo.fromPage(page)
        );
    }

    public static ClubListRes fromList(List<Club> clubs, Member reqMember) {
        return new ClubListRes(
                ClubData.fromEntities(clubs, reqMember),
                null);
    }

//    public static ClubListRes fromPage(Page<Club> page, Member reqMember) {
//        return new ClubListRes(
//                ClubData.fromEntities(page.getContent(), reqMember),
//                PageInfo.fromPage(page)
//        );
//    }

    public static ClubListRes fromClubMemberList(List<ClubMember> clubMembers) {
        List<Club> clubs = clubMembers.stream().map(ClubMember::getClub).toList();

        return new ClubListRes(
                ClubData.fromEntities(clubs, null),
                null
        );
    }

//    public static ClubListRes fromClubBookmarkPage(Page<ClubBookmark> page, Member reqMember) {
//        List<Club> clubs = page.getContent().stream().map(ClubBookmark::getClub).toList();
//
//        return new ClubListRes(
//                ClubData.fromEntities(clubs, reqMember),
//                PageInfo.fromPage(page)
//        );
//    }

}

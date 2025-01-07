package com.ariari.ariari.domain.club.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.dto.ClubData;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubListRes {

    private List<ClubData> clubDataList;
    private PageInfo pageInfo;

    public static ClubListRes fromList(List<Club> clubs, Member reqMember) {
        return new ClubListRes(
                ClubData.fromEntities(clubs, reqMember),
                null);
        }

    public static ClubListRes fromPage(Page<Club> page, Member reqMember) {
        return new ClubListRes(
                ClubData.fromEntities(page.getContent(), reqMember),
                PageInfo.fromPage(page)
        );
    }

    public static ClubListRes fromClubMemberPage(Page<ClubMember> page, Member reqMember) {
        List<Club> clubs = page.getContent().stream().map(ClubMember::getClub).toList();

        return new ClubListRes(
                ClubData.fromEntities(clubs, reqMember),
                PageInfo.fromPage(page)
        );
    }

    public static ClubListRes fromClubBookmarkPage(Page<ClubBookmark> page, Member reqMember) {
        List<Club> clubs = page.getContent().stream().map(ClubBookmark::getClub).toList();

        return new ClubListRes(
                ClubData.fromEntities(clubs, reqMember),
                PageInfo.fromPage(page)
        );
    }

}

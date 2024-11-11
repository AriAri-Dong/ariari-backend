package com.ariari.ariari.domain.club.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.member.Member;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ClubListRes {

    private List<ClubData> contents;
    private PageInfo pageInfo;

    public static ClubListRes fromList(List<Club> clubs, Member reqMember) {
        ClubListRes clubListRes = new ClubListRes();
        clubListRes.setContents(ClubData.fromEntities(clubs, reqMember));
        return clubListRes;
    }

    public static ClubListRes fromPage(Page<Club> page, Member reqMember) {
        ClubListRes clubListRes = new ClubListRes();
        clubListRes.setContents(ClubData.fromEntities(page.getContent(), reqMember));
        clubListRes.setPageInfo(PageInfo.fromPage(page));
        return clubListRes;
    }

    public static ClubListRes fromClubMemberPage(Page<ClubMember> page, Member reqMember) {
        ClubListRes clubListRes = new ClubListRes();
        List<Club> clubs = page.getContent().stream().map(ClubMember::getClub).toList();

        clubListRes.setContents(ClubData.fromEntities(clubs, reqMember));
        clubListRes.setPageInfo(PageInfo.fromPage(page));
        return clubListRes;
    }

}

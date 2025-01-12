package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import lombok.Data;

@Data
public class ClubNoticeSaveReq {

    private String title;
    private String body;

    public ClubNotice toEntity(Club club, ClubMember clubMember) {
        return new ClubNotice(
                title,
                body,
                club,
                clubMember
        );
    }

}

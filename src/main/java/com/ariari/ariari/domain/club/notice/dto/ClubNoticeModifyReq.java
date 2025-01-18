package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClubNoticeModifyReq {

    private String title;
    private String body;
    List<Long> deletedImageIds = new ArrayList<>();

    public void modifyEntity(ClubNotice clubNotice) {
        clubNotice.modify(title, body);
    }

}

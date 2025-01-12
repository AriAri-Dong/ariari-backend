package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.notice.image.dto.ClubNoticeImageData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubNoticeDetailRes {

    private ClubNoticeData clubNoticeData;
    private ClubMemberData clubMemberData;
    private List<ClubNoticeImageData> clubNoticeImageDataList;

    public static ClubNoticeDetailRes createRes(ClubNotice clubNotice) {
        return new ClubNoticeDetailRes(
                ClubNoticeData.fromEntity(clubNotice),
                ClubMemberData.fromEntity(clubNotice.getClubMember()),
                ClubNoticeImageData.fromEntities(clubNotice.getClubNoticeImages())
        );
    }

}

package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.notice.image.dto.ClubNoticeImageData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 공지사항 상세 응답 (clubMemberData 는 작성한 동아리 회원의 데이터입니다.)")
public class ClubNoticeDetailRes {

    private ClubNoticeData clubNoticeData;
    private ClubMemberData clubMemberData;
    @Schema(description = "동아리 공지사항 이미지 데이터 리스트")
    private List<ClubNoticeImageData> clubNoticeImageDataList;

    public static ClubNoticeDetailRes createRes(ClubNotice clubNotice, ClubMember clubMember) {
        return new ClubNoticeDetailRes(
                ClubNoticeData.fromEntity(clubNotice),
                clubMember == null ? null : ClubMemberData.fromEntity(clubMember),
                ClubNoticeImageData.fromEntities(clubNotice.getClubNoticeImages())
        );
    }

}

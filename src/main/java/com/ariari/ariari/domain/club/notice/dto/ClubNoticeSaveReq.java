package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "동아리 공지사항 등록 형식")
public class ClubNoticeSaveReq {

    @Schema(description = "동아리 공지사항 제목", example = "아리아리에서 개발한 서비스의 배포 시작")
    private String title;
    @Schema(description = "동아리 공지사항 내용", example = "아리아리에서 개발한 동아리 커뮤니티 서비스의 배포가 시작되었습니다!")
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

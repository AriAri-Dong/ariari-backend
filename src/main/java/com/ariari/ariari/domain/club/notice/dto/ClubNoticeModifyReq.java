package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "동아리 공지사항 수정 형식")
public class ClubNoticeModifyReq {

    @Schema(description = "동아리 공지사항 제목", example = "수정된 아리아리에서 개발한 서비스의 배포 시작")
    private String title;
    @Schema(description = "동아리 공지사항 내용", example = "수정된 아리아리에서 개발한 동아리 커뮤니티 서비스의 배포가 시작되었습니다!")
    private String body;
    @Schema(description = "동아리 공지사항 상단 고정 여부", example = "true or false")
    private Boolean isFixed;
    @Schema(description = "삭제할 공지사항 이미지 id 리스트", example = "[673012345142938986, 673012345142938987, 673012345142938988, 673012345142938989]")
    List<Long> deletedImageIds = new ArrayList<>();

    public void modifyEntity(ClubNotice clubNotice) {
        clubNotice.modify(title, body, isFixed);
    }

}

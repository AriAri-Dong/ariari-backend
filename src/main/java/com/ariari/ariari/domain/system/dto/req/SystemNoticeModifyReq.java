package com.ariari.ariari.domain.system.dto.req;


import com.ariari.ariari.domain.system.SystemNotice;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "서비스 공지사항 수정 형식")
public class SystemNoticeModifyReq {

    @Schema(description = "서비스 공지사항 제목", example = "수정된 아리아리에서 개발한 서비스의 배포 시작")
    private String title;
    @Schema(description = "서비스 공지사항 내용", example = "수정된 아리아리에서 개발한 동아리 커뮤니티 서비스의 배포가 시작되었습니다!")
    private String body;

    public void modifyEntity(SystemNotice notice) {

    }
}






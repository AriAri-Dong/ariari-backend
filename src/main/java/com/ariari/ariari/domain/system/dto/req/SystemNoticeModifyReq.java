package com.ariari.ariari.domain.system.dto.req;


import com.ariari.ariari.domain.system.SystemNotice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "서비스 공지사항 수정 형식")
@Getter
public class SystemNoticeModifyReq {

    @Schema(description = "서비스 공지사항 제목", example = "수정된 아리아리에서 개발한 서비스의 배포 시작")
    private String title;
    @Schema(description = "서비스 공지사항 내용", example = "수정된 아리아리에서 개발한 동아리 커뮤니티 서비스의 배포가 시작되었습니다!")
    private String body;
    @Schema(description = "삭제할 공지사항 이미지 id 리스트", example = "[673012345142938986, 673012345142938987, 673012345142938988, 673012345142938989]")
    List<Long> deletedImageIds = new ArrayList<>();

    public void modifyEntity(SystemNotice systemNotice) {
        systemNotice.modify(title, body);
    }
}






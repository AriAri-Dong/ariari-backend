package com.ariari.ariari.domain.system.dto.req;

import com.ariari.ariari.domain.club.notice.image.dto.ClubNoticeImageData;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.system.SystemNotice;
import com.ariari.ariari.domain.system.image.dto.SystemNoticeImageData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "서비스 공지사항 저장 형식")
@Getter
public class SystemNoticeSaveReq {

    @Schema(description = "서비스 공지사항 제목", example = "아리아리에서 개발한 서비스의 배포 시작")
    private String title;
    @Schema(description = "서비스 공지사항 내용", example = "아리아리에서 개발한 동아리 커뮤니티 서비스의 배포가 시작되었습니다!")
    private String body;
    
    public SystemNotice toEntity(String title, String body, Member member) {
        return SystemNotice.create(title,body, member);
    }


}

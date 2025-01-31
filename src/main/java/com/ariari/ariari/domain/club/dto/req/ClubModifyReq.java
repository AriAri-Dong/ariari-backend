package com.ariari.ariari.domain.club.dto.req;

import com.ariari.ariari.domain.club.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "동아리 수정 형식")
public class ClubModifyReq {

    @Schema(description = "동아리 한 줄 소개", example = "아리아리는 동아리 커뮤니티 서비스를 개발하는 동아리지롱")
    private String body;

    public void modifyEntity(Club club) {
        club.setBody(body);
    }

}

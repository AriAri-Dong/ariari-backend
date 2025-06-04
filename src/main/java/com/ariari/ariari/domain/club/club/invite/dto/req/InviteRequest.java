package com.ariari.ariari.domain.club.club.invite.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "동아리 초대 응답 형식")
public class InviteRequest {

    @Schema(description = "동아리 초대 키", example = "5345f5gddras")
    private String inviteKey;
    @Schema(description = "동아리 회원 이름", example = "홍길동")
    private String name;


}

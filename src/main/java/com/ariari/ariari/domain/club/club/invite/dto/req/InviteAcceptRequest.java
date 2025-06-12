package com.ariari.ariari.domain.club.club.invite.dto.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "동아리 초대 수락 형식")
public class InviteAcceptRequest {

    @Schema(description = "동아리 회원 이름", example = "홍길동")
    private String name;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "초대하는 동아리 아이디", example = "3453453453")
    private Long clubId;
}

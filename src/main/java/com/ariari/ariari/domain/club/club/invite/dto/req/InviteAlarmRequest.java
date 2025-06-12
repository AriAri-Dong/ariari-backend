package com.ariari.ariari.domain.club.club.invite.dto.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "동아리 초대 알림 형식")
public class InviteAlarmRequest {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "초대 유저 아이디", example = "720970760776991695")
    private Long memberId;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "초대하는 동아리 아이디", example = "720970760776991695")
    private Long clubId;

}

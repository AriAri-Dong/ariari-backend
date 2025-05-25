package com.ariari.ariari.domain.club.club.invite.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "동아리 초대 응답 형식")
public class InviteDetailRes {

    @Schema(description = "동아리 초대 키", example = "5345fgddras")
    private String key;
    @Schema(description = "동아리 아이디", example = "63552413")
    private Long clubId;

    private InviteDetailRes(String key, Long clubId){
        this.key = key;
        this.clubId = clubId;
    }


    public static InviteDetailRes of(String key, Long clubId){
        return new InviteDetailRes(key, clubId);
    }
}

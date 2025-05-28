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
    private String clubName;

    private InviteDetailRes(String key, String clubName){
        this.key = key;
        this.clubName = clubName;
    }


    public static InviteDetailRes of(String key, String clubName){
        return new InviteDetailRes(key, clubName);
    }
}

package com.ariari.ariari.domain.club.club.invite.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "동아리 초대 응답 형식")
public class InviteDetailRes {

    @Schema(description = "동아리 초대 키", example = "5345fgddras")
    private Long clubId;
    @Schema(description = "동아리 아이디", example = "63552413")
    private String clubName;

    private InviteDetailRes(Long clubId, String clubName){
        this.clubId = clubId;
        this.clubName = clubName;
    }


    public static InviteDetailRes of(Long clubId, String clubName){
        return new InviteDetailRes(clubId, clubName);
    }
}

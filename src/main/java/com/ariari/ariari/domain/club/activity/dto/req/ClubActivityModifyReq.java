package com.ariari.ariari.domain.club.activity.dto.req;

import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.enums.AccessType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ClubActivityModifyReq {
    @Schema(description = "전체 공개인지, CLUB_MEMBER or All", example = "")
    @NotNull(message = "AccessType이 null일 수 없습니다.")
    private AccessType accessType;

    @Schema(description = "본문", example = "2025년 4월 10일 아리아리 회의")
    @NotNull(message = "Body가 null일 수 없습니다. 빈 문자열이라도 필요합니다.")
    private String body;

    @Schema(description = "새로 첨부된 이미지 리스트", example = "")
    private List<MultipartFile> newImages;

    @JsonSerialize(contentUsing = ToStringSerializer.class)
    @Schema(description = "삭제해야할 이미지 리스트", example = "")
    private List<Long> deletedImageIds;

    public void modifyClubActivity(ClubActivity clubActivity){
        clubActivity.setBody(this.body);
        clubActivity.setAccessType(this.accessType);
    }
}

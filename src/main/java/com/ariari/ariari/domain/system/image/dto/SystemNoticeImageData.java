package com.ariari.ariari.domain.system.image.dto;

import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import com.ariari.ariari.domain.club.notice.image.dto.ClubNoticeImageData;
import com.ariari.ariari.domain.system.image.SystemNoticeImage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SystemNoticeImageData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "이미지 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "이미지 URI")
    private String imageUri;


    private SystemNoticeImageData(Long id, String imageUri) {
        this.id = id;
        this.imageUri = imageUri;
    }

    public static SystemNoticeImageData fromEntity(SystemNoticeImage image) {
        return new SystemNoticeImageData(
                image.getId(),
                image.getImageUri()
        );
    }

    public static List<SystemNoticeImageData> fromEntities(List<SystemNoticeImage> images) {
        return images.stream().map(SystemNoticeImageData::fromEntity).toList();
    }

}

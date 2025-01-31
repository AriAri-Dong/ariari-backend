package com.ariari.ariari.domain.club.notice.image.dto;

import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 공지사항 이미지 데이터")
public class ClubNoticeImageData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "이미지 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "이미지 URI")
    private String imageUri;

    public static ClubNoticeImageData fromEntity(ClubNoticeImage image) {
        return new ClubNoticeImageData(
                image.getId(),
                image.getImageUri()
        );
    }

    public static List<ClubNoticeImageData> fromEntities(List<ClubNoticeImage> images) {
        return images.stream().map(ClubNoticeImageData::fromEntity).toList();
    }

}

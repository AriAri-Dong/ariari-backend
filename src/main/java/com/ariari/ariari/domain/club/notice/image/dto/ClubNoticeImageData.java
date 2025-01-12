package com.ariari.ariari.domain.club.notice.image.dto;

import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubNoticeImageData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
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

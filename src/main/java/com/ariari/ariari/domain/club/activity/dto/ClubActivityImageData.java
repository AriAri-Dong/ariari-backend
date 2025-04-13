package com.ariari.ariari.domain.club.activity.dto;

import com.ariari.ariari.domain.club.activity.image.ClubActivityImage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubActivityImageData {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "활동내역 이미지 id", example = "")
    private Long id;

    @Schema(description = "image uri", example = "")
    private String imageUri;

    public static ClubActivityImageData fromEntity(ClubActivityImage clubActivityImage) {
        return ClubActivityImageData.builder()
                .id(clubActivityImage.getId())
                .imageUri(clubActivityImage.getImageUri())
                .build();
    }

    public static List<ClubActivityImageData> toEntityList(List<ClubActivityImage> clubActivityImages) {
        List<ClubActivityImageData> clubActivityImageDataList = new ArrayList<>();
        for (ClubActivityImage clubActivityImage : clubActivityImages) {
            clubActivityImageDataList.add(ClubActivityImageData.fromEntity(clubActivityImage));
        }
        return clubActivityImageDataList;
    }
}

package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import com.ariari.ariari.domain.club.notice.image.dto.ClubNoticeImageData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ClubNoticeData {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String body;
    private Boolean isFixed;
    private LocalDateTime createdDateTime;

    private ClubNoticeImageData clubNoticeImageData;

    public static ClubNoticeData fromEntity(ClubNotice clubNotice) {
        List<ClubNoticeImage> images = clubNotice.getClubNoticeImages();
        return new ClubNoticeData(
                clubNotice.getId(),
                clubNotice.getTitle(),
                clubNotice.getBody(),
                clubNotice.getIsFixed(),
                clubNotice.getCreatedDateTime(),
                images.isEmpty() ? null : ClubNoticeImageData.fromEntity(images.get(0))
        );
    }

    public static List<ClubNoticeData> fromEntities(List<ClubNotice> clubNotices) {
        return clubNotices.stream().map(ClubNoticeData::fromEntity).toList();
    }

}

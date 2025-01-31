package com.ariari.ariari.domain.club.notice.dto;

import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import com.ariari.ariari.domain.club.notice.image.dto.ClubNoticeImageData;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 공지사항 데이터")
public class ClubNoticeData {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "동아리 공지사항 id", example = "673012345142938986")
    private Long id;
    @Schema(description = "동아리 공지사항 제목", example = "아리아리에서 개발한 서비스의 배포 시작")
    private String title;
    @Schema(description = "동아리 공지사항 내용", example = "아리아리에서 개발한 동아리 커뮤니티 서비스의 배포가 시작되었습니다!")
    private String body;
    @Schema(description = "동아리 공지사항 고정 여부", example = "true")
    private Boolean isFixed;
    @Schema(description = "동아리 공지사항 생성 날짜/시간", example = "2025-01-31T09:08:18.467Z")
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

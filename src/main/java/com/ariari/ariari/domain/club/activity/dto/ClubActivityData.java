package com.ariari.ariari.domain.club.activity.dto;

import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.enums.AccessType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubActivityData {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "활동내역 id", example = "")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "작성자 id", example = "")
    private Long creatorId;

    @Schema(description = "작성자명 , null이라면 (이거 뭐였지?)", example = "")
    private String creatorName;

    @Schema(description = "작성일자", example = "")
    private LocalDateTime createdDateTime;

    @Schema(description = "수정일자", example = "")
    private LocalDateTime updatedDateTime;

    @Schema(description = "접근 권한", example = "")
    private AccessType accessType;

    @Schema(description = "본문", example = "")
    private String body;

    @Schema(description = "이미지 객체 리스트", example = "")
    private List<ClubActivityImageData> clubActivityImageDataList;

    @Schema(description = "좋아요 수", example = "")
    private Integer likeCount;

    @Schema(description = "본인이 좋아요 눌렀었는지", example = "")
    private boolean isMyLiked;

    @Schema(description = "댓글 수", example = "")
    private Integer commentCount;

    public static ClubActivityData fromEntity(ClubActivity clubActivity, Optional<ClubMember> clubMember, List<ClubActivityImageData> clubActivityImageDataList,
                                              Integer likeCount, boolean isMyLiked, Integer commentCount) {
        return ClubActivityData
                .builder()
                .id(clubActivity.getId())
                .creatorId(clubActivity.getMember().getId())
                .creatorName(clubMember.isPresent() ? clubMember.get().getName() : null)
                .createdDateTime(clubActivity.getCreatedDateTime())
                .updatedDateTime(clubActivity.getUpdatedDateTime())
                .accessType(clubActivity.getAccessType())
                .body(clubActivity.getBody())
                .clubActivityImageDataList(clubActivityImageDataList)
                .likeCount(likeCount)
                .isMyLiked(isMyLiked)
                .commentCount(commentCount)
                .build();
    }
}

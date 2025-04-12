package com.ariari.ariari.domain.club.activity.dto;

import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.activity.comment.QClubActivityComment;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubActivityCommentData {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "", example = "")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "작성자 id", example = "")
    private Long creatorId;

    @Schema(description = "", example = "")
    private String creatorName;

    @Schema(description = "작성일자", example = "")
    private LocalDateTime createdDateTime;

    @Schema(description = "수정일자", example = "")
    private LocalDateTime updatedDateTime;

    @Schema(description = "본문", example = "")
    private String body;

    @Schema(description = "좋아요 수", example = "")
    private Integer likeCount;

    @Schema(description = "본인이 좋아요 눌렀었는지", example = "")
    private boolean isMyLiked;

    @Schema(description = "본인이 차단했던 유저인지", example = "")
    private boolean isBlocked;

    public static ClubActivityCommentData fromEntity(ClubActivityComment clubActivityComment, Optional<ClubMember> clubMember,
                                              int likeCount, boolean isMyLiked, boolean isBlocked) {
        return ClubActivityCommentData.builder()
                .id(clubActivityComment.getId())
                .creatorId(clubActivityComment.getMember().getId())
                .creatorName(clubMember.isPresent() ? clubMember.get().getName() : null)
                .createdDateTime(clubActivityComment.getCreatedDateTime())
                .updatedDateTime(clubActivityComment.getUpdatedDateTime())
                .body(clubActivityComment.getBody())
                .likeCount(likeCount)
                .isMyLiked(isMyLiked)
                .isBlocked(isBlocked)
                .build();
    }
}

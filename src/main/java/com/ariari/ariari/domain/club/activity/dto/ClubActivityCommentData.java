package com.ariari.ariari.domain.club.activity.dto;

import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.activity.comment.QClubActivityComment;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.enums.ProfileType;
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

    @Schema(description = "작성자명", example = "")
    private String creatorName;

    @Schema(description = "댓글 작성자 프로필사진", example = "")
    private ProfileType creatorProfileType;

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

    @Schema(description = "본인이 댓글인지", example = "")
    private boolean isMine;

    @Schema(description = "본인이 차단했던 유저인지", example = "")
    private boolean isBlocked;

//    public static ClubActivityCommentData fromEntity(ClubActivityComment clubActivityComment, Optional<ClubMember> clubMember,
//                                              int likeCount, boolean isMyLiked, boolean isBlocked) {
//        Long creatorId = clubMember
//                .map(ClubMember::getMember)
//                .map(Member::getId)
//                .orElse(null);
//        String creatorName = clubMember
//                .map(ClubMember::getName)
//                .orElse(null);
//        ProfileType creatorProfileType = clubMember
//                .map(ClubMember::getMember)
//                .map(Member::getProfileType)
//                .orElse(null);
//
//        return ClubActivityCommentData.builder()
//                .id(clubActivityComment.getId())
//                .creatorId(creatorId)
//                .creatorName(creatorName)
//                .creatorProfileType(creatorProfileType)
//                .createdDateTime(clubActivityComment.getCreatedDateTime())
//                .updatedDateTime(clubActivityComment.getUpdatedDateTime())
//                .body(clubActivityComment.getBody())
//                .likeCount(likeCount)
//                .isMyLiked(isMyLiked)
//                .isMine(false)
//                .isBlocked(isBlocked)
//                .build();
//    }

    public static ClubActivityCommentData fromEntity(ClubActivityComment clubActivityComment, ClubMember clubMember, Member member,
                                                     int likeCount, boolean isMyLiked, boolean isBlocked) {
        Long creatorId = (member != null) ? member.getId() : null;
        String creatorName = (clubMember != null) ? clubMember.getName() : null;
        ProfileType creatorProfileType = (member != null) ? member.getProfileType() : null;

        return ClubActivityCommentData.builder()
                .id(clubActivityComment.getId())
                .creatorId(creatorId)
                .creatorName(creatorName)
                .creatorProfileType(creatorProfileType)
                .createdDateTime(clubActivityComment.getCreatedDateTime())
                .updatedDateTime(clubActivityComment.getUpdatedDateTime())
                .body(clubActivityComment.getBody())
                .likeCount(likeCount)
                .isMyLiked(isMyLiked)
                .isMine(false)
                .isBlocked(isBlocked)
                .build();
    }

//    public static ClubActivityCommentData fromEntity(ClubActivityComment clubActivityComment, Optional<ClubMember> clubMember,
//                                                     int likeCount, boolean isMyLiked, boolean isBlocked, Member reqMember) {
//        Long creatorId = clubMember
//                .map(ClubMember::getMember)
//                .map(Member::getId)
//                .orElse(null);
//        String creatorName = clubMember
//                .map(ClubMember::getName)
//                .orElse(null);
//        ProfileType creatorProfileType = clubMember
//                .map(ClubMember::getMember)
//                .map(Member::getProfileType)
//                .orElse(null);
//
//        return ClubActivityCommentData.builder()
//                .id(clubActivityComment.getId())
//                .creatorId(creatorId)
//                .creatorName(creatorName)
//                .creatorProfileType(creatorProfileType)
//                .createdDateTime(clubActivityComment.getCreatedDateTime())
//                .updatedDateTime(clubActivityComment.getUpdatedDateTime())
//                .body(clubActivityComment.getBody())
//                .likeCount(likeCount)
//                .isMine(reqMember.equals(clubActivityComment.getMember()))
//                .isMyLiked(isMyLiked)
//                .isBlocked(isBlocked)
//                .build();
//    }

    public static ClubActivityCommentData fromEntity(ClubActivityComment clubActivityComment,  ClubMember clubMember, Member member,
                                                     int likeCount, boolean isMyLiked, boolean isBlocked, Member reqMember) {
        Long creatorId = (member != null) ? member.getId() : null;
        String creatorName = (clubMember != null) ? clubMember.getName() : null;
        ProfileType creatorProfileType = (member != null) ? member.getProfileType() : null;

        return ClubActivityCommentData.builder()
                .id(clubActivityComment.getId())
                .creatorId(creatorId)
                .creatorName(creatorName)
                .creatorProfileType(creatorProfileType)
                .createdDateTime(clubActivityComment.getCreatedDateTime())
                .updatedDateTime(clubActivityComment.getUpdatedDateTime())
                .body(clubActivityComment.getBody())
                .likeCount(likeCount)
                .isMine(reqMember.equals(clubActivityComment.getMember()))
                .isMyLiked(isMyLiked)
                .isBlocked(isBlocked)
                .build();
    }
}

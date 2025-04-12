package com.ariari.ariari.domain.club.activity.dto.res;

import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityCommentData;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityData;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityImageData;
import com.ariari.ariari.domain.club.activity.enums.AccessType;
import com.ariari.ariari.domain.club.activity.image.ClubActivityImage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ClubActivityDetailRes{
    @Schema(description = "활동후기 전반적인 내용", example = "")
    private ClubActivityData clubActivityData;

    @Schema(description = "댓글 리스트 객체", example = "")
    private List<ClubActivityCommentRes> ClubActivityCommentResList = new ArrayList<>();

    @Data
    @NoArgsConstructor
    public static class ClubActivityCommentRes{
        @Schema(description = "부모 댓글 데이터", example = "")
        private ClubActivityCommentData commentData;

        @Schema(description = "자식 댓글 데이터 리스트", example = "")
        private List<ClubActivityCommentData> commentDataList = new ArrayList<>();
    }
}
